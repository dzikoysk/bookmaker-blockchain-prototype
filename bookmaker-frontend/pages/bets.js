import Layout from "../components/Layout";
import {ChevronLeftIcon, ChevronRightIcon} from "@chakra-ui/icons"
import { Badge, Text, Button, Card, CardBody, CardFooter, Container, Flex, Heading, IconButton, Image, Stack, Tag, Link, LinkBox } from "@chakra-ui/react"
import bets from '../data/bets-feed'
import categories from '../data/bets-categories-feed'

export default function Bets() {
  return (
    <Layout>
      <Container maxW='container.lg' minH={'90wh'} marginInlineStart={{ base: '0', sm: 'auto' }}>
        <Flex>
          <Stack>
            {categories.map((category, index) => (
              <Stack w='48' key={`category-${index}`}>
                <Heading justifyContent='flex-start' padding={2} fontSize='sm'>
                  {category.name}
                </Heading>
                {category.subcategories.map((subcategory, index) => (
                  <Text
                    key={`subcategory-${index}`}
                    paddingLeft={4}
                    justifyContent='flex-start'
                    color={subcategory.selected ? 'purple.500' : 'black'}
                    fontWeight={subcategory.selected ? 'bold' : 'normal'}
                    fontSize='sm'
                  >
                    {subcategory.name}
                  </Text>
                ))}
              </Stack>
            ))}
          </Stack>
          <Stack justifyContent={'center'}>
            {bets.map((bet, index) => {
              return (
                <Card
                  key={`bet-${index}`}
                  direction={{ base: 'column', md: 'row' }}
                  overflow='hidden'
                  variant='outline'
                  width={{ base: '100%' }}
                  fontSize='sm'
                >
                  <Image
                    objectFit='contain'
                    maxW={{ base: '100%', md: '300px' }}
                    marginLeft={5}
                    src={bet.logo}
                    filter={bet.result != undefined ? 'grayscale(100%)' : 'none'}
                  />
                  <Stack>
                    <CardBody paddingTop={2} paddingBottom={0}>
                      <Badge>{bet.event}</Badge>
                      {bet.open
                        ? <Badge colorScheme='green' marginLeft={2} marginBottom={1}>Trwający</Badge>
                        : <Badge colorScheme='pink' marginLeft={2} marginBottom={1}>Zakończony</Badge>
                      }
                      <Heading size='md' paddingTop='2' color={ bet.open ? 'black' : 'darkgray' }>
                        {bet.title}
                      </Heading>
                      <Text color={bet.open ? '' : 'gray'} >{bet.date}</Text>
                      <Text color={bet.open ? '' : 'gray'} py='2'>{bet.description}</Text>
                    </CardBody>
                    <CardFooter paddingTop={0} paddingBottom={3}>
                      {bet.odds.map((odd, index) => {
                          return (
                            <Tag
                              key={`odd-${index}`}
                              size={'md'}
                              colorScheme={bet.open ? 'gray' : 'gray'}
                              borderRadius={'md'}
                              marginRight={'2'}
                            >
                              <Text>{odd.name}: <Text as="span" fontWeight={'bold'}>{odd.value}</Text></Text>
                            </Tag>
                          )
                      })}
                      <Link href="/bet/example">
                        <Button size='sm' variant='outline' colorScheme={bet.open ? 'black' : 'gray'}>
                          Zobacz szczegóły
                        </Button>
                      </Link>
                    </CardFooter>
                  </Stack>
                </Card>
              )
            })}
            <Flex justifyContent={'center'} paddingTop={3}>
              <Link href={'/bets'}>
                <IconButton
                  variant={'outline'}
                  icon={<ChevronLeftIcon />}
                  marginX={'1'}
                />
              </Link>
              {bets.map((bet, index) => {
                return (
                  <Link href={'/bets'} key={`bet-list-${index}`}>
                    <IconButton
                      variant={'outline'}
                      marginX={'1'}
                      icon={<Text>{index}</Text>}
                    />
                  </Link>
                )
              })}
              <Link href={'/bets'}>
                <IconButton
                  variant={'outline'}
                  icon={<ChevronRightIcon />}
                  marginX={'1'}
                />
              </Link>
            </Flex>
          </Stack>
        </Flex>
      </Container>
    </Layout>
  )
}
