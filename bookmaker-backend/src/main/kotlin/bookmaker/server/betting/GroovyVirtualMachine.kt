package bookmaker.server.betting

import groovy.lang.GroovyClassLoader
import org.codehaus.groovy.control.CompilerConfiguration
import bookmaker.blockchain.BettingBlockchainVirtualMachine
import bookmaker.blockchain.SmartContract
import bookmaker.blockchain.SmartContractApi
import bookmaker.blockchain.SmartContractSource
import bookmaker.server.BookmakerApplication

class GroovyVirtualMachine(private val smartContractApi: SmartContractApi) : BettingBlockchainVirtualMachine {

    override fun runSmartContract(smartContractCode: SmartContractSource): SmartContract =
        GroovyClassLoader(BookmakerApplication::class.java.classLoader, CompilerConfiguration())
            .parseClass(smartContractCode)
            .getConstructor(SmartContractApi::class.java)
            .newInstance(smartContractApi) as SmartContract

}