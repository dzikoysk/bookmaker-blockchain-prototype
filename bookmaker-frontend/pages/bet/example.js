import Layout from "../../components/Layout";
import {
  Box, Button, Container, Image, NumberDecrementStepper, NumberIncrementStepper, NumberInput, NumberInputField, NumberInputStepper, Stat, StatGroup, StatHelpText, StatLabel, StatNumber, Text,
  Table,
  Thead,
  Tbody,
  Tr,
  Th,
  Td,
  TableCaption,
  TableContainer,
  CircularProgress,
  Avatar,
  useDisclosure,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalBody,
  ModalFooter,
  ModalCloseButton
} from '@chakra-ui/react';
import { Flex } from '@chakra-ui/react';
import { Stack } from '@chakra-ui/react';
import { Heading } from '@chakra-ui/react';
import { useState } from "react";
// import { Prism as SyntaxHighlighter } from 'react-syntax-highlighter'
import { vs as light } from 'react-syntax-highlighter/dist/cjs/styles/prism'
import { LockIcon } from "@chakra-ui/icons";
import { Link } from '@chakra-ui/react';
import { FaUserCircle } from "react-icons/fa";
import dynamic from 'next/dynamic'
import smartContractCode from '../../data/bets-smart-contract.js'

const SyntaxHighlighter = dynamic(() => import('react-syntax-highlighter').then(it => it.Prism), {
  ssr: false,
})

const BetButton = ({ team, odds, selected }) => {
  return (
    <Button
      colorScheme={selected ? 'purple' : 'black'}
      h='auto'
      w='40%'
      variant={selected ? 'solid' : 'outline'}
    >
      <Stack paddingY={3} paddingX={3}>
        <Text>{team}</Text>
        <Text>{odds}</Text>
      </Stack>
    </Button>
  ) 
}

const SummaryStat = ({ label, number, help }) => {
  return (
    <Flex flexDirection='column'>
      <Box fontSize='sm' fontWeight={'medium'}>{label}</Box>
      <Box fontSize='2xl' fontWeight='semibold'>{number}</Box>
      <Box fontSize='sm' opacity="0.8">{help}</Box>
    </Flex>
    )
  }

export default function Bet1() {
  const format = (val) => parseFloat(val).toFixed(2) + ' PLN'
  const parse = (val) => val.replace(/^\PLN/, '')
  const [value, setValue] = useState('20.00')

  const contractCode = `
transaction {
  placeBet(
    betId: "f8c3de3d-1fea-4d7c-29f63c4c3454",
    userId: "123e4567-e89b-12d3-426614174000",
    value: 20.00,
    on: "Fnatic"
  )
}
  `

  const { isOpen, onOpen, onClose } = useDisclosure()

  const user1 ='123e4567-e89b-12d3-426614174000'
  const user2 ='abc14df4-5e6f-4ff2-990427111abc'
  const user3 ='09a8b7c6-5d4e-3c2b-1a0987654321'

  return (
    <Layout>
      <Modal closeOnOverlayClick={false} isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent maxW='fit-content'>
          <ModalHeader>Smart kontrakt obslugujący zakład</ModalHeader>
          <ModalCloseButton />
          <ModalBody pb={2} >
            <SyntaxHighlighter
              language="groovy"
              style={light}
              customStyle={{
                height: '75vh',
                margin: '10px',
                overflowX: 'hidden',
                border: 'none',
                borderRadius: '10px',
                fontSize: '0.8rem',
                background: 'white',
              }}
            >
              {smartContractCode.trim()}
            </SyntaxHighlighter>
          </ModalBody>
          <ModalFooter>
            <Button onClick={onClose}>Zamknij</Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
      <Container maxW='container.lg'>
        <Flex paddingTop='7' flexDir={{ base: 'column', md: 'row' }}>
          <Stack w={{ base: '100%', md: '55%' }} paddingRight={{ base: 0, md: 10 }} paddingBottom={10}>
            <Heading size='md' textAlign='center' paddingBottom='4'>
              LEC Spring 2023 - Fnatic vs G2 Esports
            </Heading>
            <Image
              src='/images/articles/fncvsg2.jpg'
              borderRadius='xl'
            />
            <Text textAlign={'justify'} paddingTop={5}>
              W sobotę o godzinie 21:00 rozpocznie się emocjonujące starcie między dwoma
              europejskimi gigantami League of Legends - Fnatic i G2 Esports,
              w ramach rozgrywek LEC Spring 2023.
              <br/><br/>
              Fnatic, 
              jeden z najbardziej utytułowanych zespołów w historii LEC,
              w tym sezonie zmaga się z pewnymi trudnościami, 
              ale wciąż pozostaje jednym z najlepszych zespołów w regionie.
              Ich styl gry opiera się na agresywnym podejściu, 
              szczególnie w pierwszej fazie gry,
              co pozwala im na zdobycie przewagi i kontrolowanie rywalizacji.
              <br/><br/>
              Z kolei G2 Esports,
              również będący jednym z czołowych zespołów w Europie,
              od początku sezonu prezentuje bardzo stabilną formę,
              co przyczyniło się do ich świetnego startu w rozgrywkach. 
              G2 Esports słynie z bardzo elastycznego stylu gry,
              dzięki czemu są w stanie dopasować się do każdego przeciwnika
              i zmienić strategię gry w zależności od potrzeb.
              <br/><br/>
              Kto zwycięży w tym spotkaniu? 
              Czas pokaże, ale jedno jest pewne - fani esportu nie powinni przegapić tego emocjonującego widowiska.
            </Text>
          </Stack>
          <Stack>
            <Stack padding={7} backgroundColor='gray.100' borderRadius={'lg'} w='full'>
              <Heading size='md' textAlign={'center'}>Obstaw</Heading>
              <Text fontSize='sm' paddingTop={1} textAlign='center'>Wybierz swojego zwycięzcę:</Text>
              <Flex justifyContent={'space-around'}  paddingTop={2}>
                <BetButton team={'Fnatic'} odds={'5.5'} selected={true} />
                <BetButton team={'G2 Esports'} odds={'1.8'} />
              </Flex>
              <Text fontSize='sm' paddingTop={3} paddingBottom={2} textAlign='center'>
                Obstawiona kwota po kursie 
                <Text as='span' fontWeight='bold' color='purple.500'> x5.5</Text>
                :
              </Text>
              <Flex paddingX={4}>
                <NumberInput
                  onChange={(valueString) => setValue(parse(valueString))}
                  value={format(value)}
                  max={50}
                  w='70%'
                >
                  <NumberInputField />
                  <NumberInputStepper>
                    <NumberIncrementStepper />
                    <NumberDecrementStepper />
                  </NumberInputStepper>
                </NumberInput>
                <Text fontSize='xl' paddingX={3}>≈</Text>
                <Text paddingTop={1.5} fontWeight='bold'>{ value * 5.5 }PLN</Text>
              </Flex>
              <Text textAlign={'center'} fontSize='sm' paddingTop={3} paddingBottom={2}>
                Przygotowana transakcja:
              </Text>
              <SyntaxHighlighter
                language="kotlin"
                style={light}
                customStyle={{
                  border: 'none',
                  borderRadius: '10px',
                  fontSize: '0.8rem',
                  width: '100%',
                  background: 'white',
                }}
              >
                {contractCode.trim()}
              </SyntaxHighlighter>
              <Link textAlign={'center'} color='purple.500' fontSize='sm' paddingTop={1} paddingBottom={2} onClick={onOpen}>
                <LockIcon paddingBottom={0.5} />
                <Text as='span' fontSize='sm' paddingLeft={2}>Zobacz pełny kontrakt ⟶</Text>
              </Link>
              <Button colorScheme={'purple'}>
                Wykonaj kontrakt
              </Button>
            </Stack>
            <Stack marginTop={4} backgroundColor='gray.100' padding={4} borderRadius='lg'>
              <Heading size='md' textAlign={'center'}>Podsumowanie</Heading>
              <Flex justifyContent='space-around' paddingTop={2}>
                <SummaryStat
                    label='W puli na FNC'
                    number='1 241 PLN'
                    help='93 zakłady'
                />
                <SummaryStat
                    label='W puli na G2'
                    number='6 183 PLN'
                    help='528 zakładów'
                />
              </Flex>
            </Stack>
          </Stack>
        </Flex>
        <Stack>
          <Heading size='md' textAlign='center' paddingTop={10} paddingBottom={4}>Ostatnie transakcje</Heading>
          <TableContainer>
            <Table variant='simple' size='sm'>
              <TableCaption>
                Automatyczne aktualizowanie zmian w blockchainie
                <CircularProgress size={5} isIndeterminate color='purple.300' marginLeft={2} />
              </TableCaption>
              <Thead>
                <Tr>
                  <Th>Nowy zakład</Th>
                  <Th>Obstawiona opcja</Th>
                  <Th isNumeric>Wartość</Th>
                </Tr>
              </Thead>
              <Tbody>
                <Tr>
                  <Td>
                    <Flex>
                      <Avatar size='xs' bg='purple.500' marginRight={2} icon={<FaUserCircle fontSize='1rem' />} />
                      <Link href={"/blockchain/fnc-g2"}><Text paddingTop={1} color='purple.500'>{user1}</Text></Link>
                    </Flex>
                  </Td>
                  <Td>FNC</Td>
                  <Td isNumeric>25.00 PLN</Td>
                </Tr>
                <Tr>
                  <Td>
                    <Flex>
                      <Avatar size='xs' bg='purple.500' marginRight={2} icon={<FaUserCircle fontSize='1rem' />} />
                      <Link href={"/blockchain/fnc-g2"}><Text paddingTop={1} color='purple.500'>{user2}</Text></Link>
                    </Flex>
                  </Td>
                  <Td>G2</Td>
                  <Td isNumeric>50.00 PLN</Td>
                </Tr>
                <Tr>
                  <Td>
                    <Flex>
                      <Avatar size='xs' bg='purple.500' marginRight={2} icon={<FaUserCircle fontSize='1rem' />} />
                      <Link href={"/blockchain/fnc-g2"}><Text paddingTop={1} color='purple.500'>{user3}</Text></Link>
                    </Flex>
                  </Td>
                  <Td>G2</Td>
                  <Td isNumeric>19.00 PLN</Td>
                </Tr>
              </Tbody>
            </Table>
          </TableContainer>
        </Stack>
      </Container>
    </Layout>
  )
}
