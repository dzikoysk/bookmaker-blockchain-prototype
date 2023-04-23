package bookmaker.server.betting

import io.javalin.http.BadRequestResponse
import bookmaker.blockchain.BettingOption
import bookmaker.blockchain.IssuedSmartContract
import bookmaker.blockchain.PLN
import bookmaker.blockchain.ResultAnnouncementOperation
import bookmaker.blockchain.SmartContractOperation
import bookmaker.blockchain.TransactionResult
import bookmaker.blockchain.UserBetOperation
import bookmaker.blockchain.contracts.ANNOUNCE_RESULT_FUNCTION
import bookmaker.blockchain.contracts.BETTING_CONTRACT_SOURCE_CODE
import bookmaker.blockchain.contracts.PLACE_BET_FUNCTION
import bookmaker.server.betting.BetRepository.CreateBetInDatabase
import bookmaker.server.user.Transfer
import bookmaker.server.user.UserService
import java.time.LocalDateTime
import java.util.UUID
import kotlin.concurrent.timerTask

class BettingService(
    private val betRepository: BetRepository,
    private val betBlockchainService: BetBlockchainService,
    private val userService: UserService,
    ) {

    private val bettingContractId = mutableMapOf<UUID, UUID>()

    data class CreateBetRequest(
        val ownerId: UUID,
        val title: String,
        val description: String,
        val startDate: LocalDateTime,
        val endDate: LocalDateTime,
        val bettingOptions: List<BettingOption>,
    )

    fun createBet(createBetRequest: CreateBetRequest): UUID {
        val bet = betRepository.createBet(
            CreateBetInDatabase(
                ownerId = createBetRequest.ownerId,
                title = createBetRequest.title,
                description = createBetRequest.description
            )
        )

        val bettingBlockchain = betBlockchainService.createBetBlockchain(
            bet = bet,
            startDate = createBetRequest.startDate,
            endDate = createBetRequest.endDate,
            bettingOptions = createBetRequest.bettingOptions
        )

        val smartContractId = betBlockchainService.registerNewBlock(
            betId = bettingBlockchain.id,
            owner = createBetRequest.ownerId,
            operation = SmartContractOperation(
                smartContractSource = BETTING_CONTRACT_SOURCE_CODE
            )
        ).getSmartContractResultAs<UUID>()

        bettingContractId[bet.id] = smartContractId
        return bet.id
    }

    data class PlaceBetRequest(
        val userId: UUID,
        val betId: UUID,
        val bettingOption: BettingOption,
        val value: PLN
    )

    fun placeBet(placeBetRequest: PlaceBetRequest): TransactionResult {
        val bet = getBet(placeBetRequest.betId) ?: throw BadRequestResponse("Unknown bet ${placeBetRequest.betId}")
        val smartContractId = bettingContractId[bet.id] ?: throw BadRequestResponse("Unknown smart contract ${placeBetRequest.betId}")
        val user = userService.findUserById(placeBetRequest.userId) ?: throw BadRequestResponse("Unknown user ${placeBetRequest.userId}")
        require(user.wallet.getBalance() >= placeBetRequest.value) { "Not enough money" }

        val result = betBlockchainService.registerNewBlock(
            betId = bet.id,
            owner = placeBetRequest.userId,
            issuedSmartContract = IssuedSmartContract(
                smartContractId = smartContractId,
                function = PLACE_BET_FUNCTION
            ),
            operation = UserBetOperation(
                bettingOption = placeBetRequest.bettingOption,
                value = placeBetRequest.value
            )
        )

        user.wallet.history.add(
            Transfer(
                title = "Bet on ${bet.title}",
                date = LocalDateTime.now(),
                value = -placeBetRequest.value,
            )
        )

        return result
    }

    fun announceResult(betId: UUID, bettingOption: BettingOption): TransactionResult {
        val bet = getBet(betId) ?: throw BadRequestResponse("Unknown bet $betId")
        val smartContractId = bettingContractId[bet.id] ?: throw BadRequestResponse("Unknown smart contract $betId")

        return betBlockchainService.registerNewBlock(
            betId = bet.id,
            owner = bet.ownerId,
            issuedSmartContract = IssuedSmartContract(
                smartContractId = smartContractId,
                function = ANNOUNCE_RESULT_FUNCTION
            ),
            operation = ResultAnnouncementOperation(
                result = bettingOption
            )
        )
    }

    fun getAllBets(): List<Bet> =
        betRepository.findAll()

    fun getBet(uuid: UUID): Bet? =
        betRepository.findBetById(uuid)

}