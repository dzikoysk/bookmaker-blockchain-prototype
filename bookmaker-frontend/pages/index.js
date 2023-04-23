import Head from "next/head"
import Layout from "../components/Layout"
import { Badge, Box, Button, Card, CardBody, Container, Flex, Heading, Image, Link, Stack, Stat, StatArrow, StatHelpText, StatLabel, StatNumber, Text } from "@chakra-ui/react"
import trendingBets from "../data/trending-feed"
import news from "../data/latest-news-feed"

export default function Home() {
  return (
    <Layout>
      <Head />
      <Flex
        w='100%'
        bgGradient={[ 'linear(to-b, white, purple.100, white)' ]}
      >
        <Container maxW='container.lg' minH={'90wh'} marginInlineStart={{ base: '0', sm: 'auto' }}>
          <Flex flexDirection={{ base: 'column-reverse', md: 'row' }} alignItems={'center'}>
            <Box maxW={'fit-content'} marginX={10}>
              <Stack justifyContent={'center'} h={{ base: '30vh', md: "40vh" }} w='auto'>
                <Heading fontFamily={'Open Sans'}>
                  Zakłady bukmacherskie <br />
                  <Heading as='span' color={'purple.500'}>+ Blockchain</Heading>
                </Heading>
                <Text fontFamily={'Open Sans'} paddingTop={2}>
                  Bezpieczne zakłady bukmacherskie na bazie blockchainu, <br/>
                  dołacz do transparentnego systemu graczy w całej Polsce!
                </Text>
                <Flex paddingTop={4} justifyContent='space-between'>
                  <Button w='48%' colorScheme={'purple'}>Rejestracja</Button>
                  <Button w='48%' variant={'outline'} colorScheme='black'>Logowanie</Button>
                </Flex>
                <Link href="/about" fontWeight={'bold'} paddingTop={4}>
                  Dowiedz się więcej ⟶
                </Link>
              </Stack>
            </Box>
            <Box>
              <Image
                src='/images/landing.png'
                alt='Landing image'
                maxH={{ base: '200px', md: '450px' }}
              />
            </Box>
          </Flex>
          <Flex w='full' marginTop={{ base: 16, md: 0 }} paddingBottom={3} paddingX={10}>
            <Stack w='full'>
              <Heading size={'md'} >Trendujące zakłady</Heading>
              <Flex w='full' maxW='100vw' justifyContent={'space-between'} paddingTop={5} paddingX={2} flexWrap={'wrap'}>
                {trendingBets.map(bet => (
                  <Stat minW='180px'>
                    <StatLabel>{bet.league}</StatLabel>
                    <StatNumber>{bet.title}</StatNumber>
                    <StatHelpText>
                      <StatArrow type='increase' />
                      {bet.fav} - {bet.odds}
                    </StatHelpText>
                  </Stat>
                ))}
              </Flex>
              <Link href="/bets" fontWeight={'bold'} color={'purple.500'} paddingTop={4}>
                Zobacz więcej ⟶
              </Link>

              <Heading size={'md'} paddingTop={20}>Wydarzenia</Heading>
              <Flex justifyContent={'space-between'} paddingTop={5} paddingX={1}>
                {news.map(article => (
                  <Card
                    direction={{ base: 'column', sm: 'row' }}
                    overflow='hidden'
                    variant='outline'
                    w='49%'
                  >
                    <Image
                      objectFit='cover'
                      maxW={{ base: '100%', sm: '200px' }}
                      src={article.img}
                    />
                    <CardBody fontSize={'sm'}>
                      <Text fontWeight={'bold'}>{article.date}</Text>
                      <Badge fontWeight={'bold'}>{article.title}</Badge>
                      <Text>
                        {article.description}
                        <Link color='purple.500' fontWeight={'bold'}> Czytaj dalej</Link>
                      </Text>
                    </CardBody>
                  </Card>
                ))}
              </Flex>
            </Stack>
          </Flex>
        </Container>
      </Flex>
    </Layout>
  )
}
