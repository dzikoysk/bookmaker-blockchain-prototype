import {
  Accordion,
  AccordionButton, AccordionIcon,
  AccordionItem, AccordionPanel, Badge,
  Box,
  Container, Flex,
  Heading, IconButton, Link,
  Stack, Stat, StatHelpText, StatLabel, StatNumber, Table, TableContainer, Tbody, Td, Text, Th, Thead, Tr
} from "@chakra-ui/react";
import Layout from "../components/Layout";
import {CalendarIcon, ChevronLeftIcon, ChevronRightIcon, TriangleDownIcon, TriangleUpIcon} from "@chakra-ui/icons";
import usersBets from '../data/wallet-feed'
import dynamic from 'next/dynamic'

function Account() {
  return <Layout>
    <Container maxW="container.lg">
      <Stack paddingTop={1}>
        <Stack
          border={'1px solid black'}
          borderRadius={'xl'}
          paddingY={4}
          paddingX={7}
          backgroundColor={'black'}
          color={'white'}
        >
          <Heading size={'md'}>
            <CalendarIcon marginRight={3} paddingBottom={1} />
            Portfel
          </Heading>
          <Flex paddingTop={4}>
            <Stat paddingRight={6}>
              <StatLabel>Saldo</StatLabel>
              <StatNumber>210.00PLN</StatNumber>
              <StatHelpText>W dniu {new Date().toLocaleDateString()}</StatHelpText>
            </Stat>
            <Stat>
              <StatLabel>Trwające zakłady</StatLabel>
              <StatNumber>4</StatNumber>
              <StatHelpText>Na kwotę 60.00PLN</StatHelpText>
            </Stat>
            <Stat>
              <StatLabel>Średnia wygranych</StatLabel>
              <StatNumber>61.21%</StatNumber>
              <StatHelpText>71W - 45L</StatHelpText>
            </Stat>
            <Stat>
              <StatLabel>Wszystkie operacje</StatLabel>
              <StatNumber>116</StatNumber>
              <StatHelpText>20.04.2022 - {new Date().toLocaleDateString()}</StatHelpText>
            </Stat>
          </Flex>
        </Stack>
        <Heading size={'md'} paddingTop={4} paddingBottom={3} paddingX={1}>Zakłady</Heading>
        <Accordion allowToggle>
          {usersBets.map((bet) => (
            <AccordionItem key={bet.betId}>
              <Heading>
                <AccordionButton>
                  <Box paddingRight={3} w={32} textAlign={'left'}>
                    {
                      bet.closed
                        ? <Badge colorScheme="red" paddingY={0.5} paddingX={2}>Zakończony</Badge>
                        : <Badge colorScheme="purple" paddingY={0.5} paddingX={2}>Trwający</Badge>
                    }
                  </Box>
                  <Flex
                    flex='1'
                    textAlign='left'
                    fontWeight={'semibold'}
                    color={ bet.closed ? 'gray.600' : 'gray.900' }
                    paddingY={2}
                  >
                    <Box w={24}>
                      {bet.title}
                    </Box>
                    { bet.closed && <Flex paddingX={3} as={'span'} flex={'1'} textAlign={'left'}>
                      { bet.result < 0
                        ? <TriangleDownIcon color='red.400' boxSize={3} marginTop={1} />
                        : <TriangleUpIcon color='green.500' boxSize={3} marginTop={1}/>
                      }
                      <Box paddingLeft={3}>
                        { bet.result > 0 ? '+' : '' }{bet.result.toFixed(2)}PLN
                      </Box>
                    </Flex> }
                  </Flex>
                  <Box
                    as='span'
                    paddingX={4}
                    fontSize={'sm'}
                  >
                    {bet.date}
                  </Box>
                  <AccordionIcon />
                </AccordionButton>
              </Heading>
              <AccordionPanel pb={4}>
                <TableContainer>
                  <Table size='sm'>
                    <Thead>
                      <Tr>
                        <Th></Th>
                        <Th>Wartość</Th>
                        <Th>Opis</Th>
                      </Tr>
                    </Thead>
                    <Tbody>
                      <Tr>
                        <Th>Nazwa</Th>
                        <Td>{bet.description}</Td>
                        <Td>Skrót</Td>
                      </Tr>
                      <Tr>
                        <Th>Data</Th>
                        <Td>{bet.date}</Td>
                        <Td>Data wydarzenia</Td>
                      </Tr>
                      <Tr>
                        <Th>Blockchain</Th>
                        <Td>
                          <Link color={'purple.500'} href='/blockchain/fnc-g2'>{bet.betId}</Link>
                        </Td>
                        <Td>Identyfiaktor blockchainu powiązanego z zakładem</Td>
                      </Tr>
                      <Tr>
                        <Th>Operacja</Th>
                        <Td>
                          <Link color={'purple.500'} href='/blockchain/fnc-g2'>{bet.operationId}</Link>
                        </Td>
                        <Td>Hash bloku z transakcją obstawienia zakładu w łańcuchu</Td>
                      </Tr>
                      <Tr>
                        <Th>Zakład</Th>
                        <Td>Wygrana {bet.bet}</Td>
                        <Td>Obstawiony przez użytkownika wynik</Td>
                      </Tr>
                      <Tr>
                        <Th>Wartość</Th>
                        <Td>{bet.result.toFixed(2)}PLN</Td>
                        <Td>Zmiana salda po klasyfikacji wyniku zakładu</Td>
                      </Tr>
                    </Tbody>
                  </Table>
                </TableContainer>
              </AccordionPanel>
            </AccordionItem>
          ))}
        </Accordion>
        <Flex justifyContent={'center'} paddingTop={3}>
          <IconButton
            variant={'outline'}
            icon={<ChevronLeftIcon />}
            marginX={'1'}
          />
          {
            usersBets
              .filter((bet, index) => index % 2 === 0)
              .map((bet, index) => (
                <IconButton
                    key={bet.betId}
                    variant={'outline'}
                    marginX={'1'}
                    icon={<Text>{index}</Text>}
                  />
                ))
          }
          <IconButton
            variant={'outline'}
            icon={<ChevronRightIcon />}
            marginX={'1'}
          />
        </Flex>
      </Stack>
    </Container>
  </Layout>
}

const DynamicAccount = dynamic(() => Promise.resolve(Account), { ssr: false })

export default function Wallet() {
  return (
    <DynamicAccount />
  )
}