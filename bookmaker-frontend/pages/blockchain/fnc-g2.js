import { 
  Accordion,
  AccordionItem,
  AccordionPanel,
  Center, 
  Container,
  Divider,
  Table, 
  TableContainer,
  Text, 
  Stack,
  Flex,
  Badge,
  Tr,
  Td,
  Th,
  Tbody,
  Heading,
  Input,
  IconButton,
  AccordionButton,
  Code,
  Link,
  Spinner,
} from '@chakra-ui/react'
import { SearchIcon } from "@chakra-ui/icons"
import Layout from './../../components/Layout'
import blockchain from '../../data/blockchain'

const BlockchainBlock = ({ block, idx }) => {
  const humanReadableDate = new Date(block.timestamp).toLocaleDateString()
  const convertContractToString = (contract) => {
    if (contract == null) return 'Brak'
    return contract.smartContractId + " (" + contract.function + ")"
  }

  return (
    <>
      <AccordionButton>
        <Flex justifyContent={'space-between'} w="full">
          <Flex>
            <Text>{idx}</Text>
            <Center width={6} >
              <Divider color={'black'} orientation='vertical'/>
            </Center>
            <Badge colorScheme={'purple'} marginLeft={2} paddingTop={0.5}>{block.operation.type}</Badge>
          </Flex>
          <Text>{humanReadableDate}</Text>
        </Flex>
      </AccordionButton>
      <AccordionPanel pb={4}>
        <TableContainer paddingLeft={5}>
          <Table size='sm'>
            <Tbody>
              <Tr>
                <Th></Th>
                <Th>Wartość</Th>
                <Th>Opis</Th>
              </Tr>
              <Tr>
                <Th>Poprzedni blok</Th>
                <Td><Link color='purple.500'>{block.parent ?? '-'}</Link></Td>
                <Td>Hash rodzica</Td>
              </Tr>
              <Tr>
                <Th>Właściciel</Th>
                <Td>{block.ownerId}</Td>
                <Td>Właściciel bloku</Td>
              </Tr>
              <Tr>
                <Th>Kontrakt</Th>
                <Td><Link color='purple.500'>{block.contract?.smartContractId ?? '-'}</Link></Td>
                <Td>Identyfikator ykorzystanego kontraktu</Td>
              </Tr>
              <Tr>
                <Th>Funkcja</Th>
                <Td><Code>{block.contract?.function ?? '-'}</Code></Td>
                <Td>Funkcja z kontraktu</Td>
              </Tr>
              <Tr>
                <Th>Operacja</Th>
                <Td>{block.operation.type ?? '-'}</Td>
                <Td>Zarejestrowana operacja</Td>
              </Tr>
              <Tr>
                <Th>Dane operacji</Th>
                <Td>
                  {Object.getOwnPropertyNames(block.operation).map((key) => (
                    <Text key={key}>
                      {key} - {block.operation[key]?.toString()}
                    </Text>
                  ))}
                </Td>
                <Td>Wartości potrzebne do obsługi operacji</Td>
              </Tr>
            </Tbody>
          </Table>
        </TableContainer>
      </AccordionPanel>
    </>
  )
}

export default function BlockchainViewOfFncVsG2() {
  return (
    <Layout>
      <Container maxW='container.xl' paddingX={10}>
        <Stack paddingTop={1} fontSize={'sm'}>
          <Stack paddingTop="1">
            <Heading size="md" pb="3">Blockchain {blockchain.id}</Heading>
            <Flex w="full">
              <Input flex="1" placeholder="Przeszukaj zawartość tego blockchainu" my="2" />
              <IconButton
                colorScheme="purple"
                marginLeft="2"
                marginTop="2"
                icon={<SearchIcon />}
              />
            </Flex>
          </Stack>
          <Accordion allowToggle>
            {blockchain.blocks.map((block, idx) => (
              <AccordionItem key={idx}>
                <BlockchainBlock block={block} idx={idx} />
              </AccordionItem>
            ))}
          </Accordion>
          <Center paddingTop={4}>
            <Spinner />
          </Center>
        </Stack>
      </Container>
    </Layout>
  )
}
