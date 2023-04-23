import {ChevronLeftIcon, ChevronRightIcon} from "@chakra-ui/icons";
import { Button, Card, CardBody, CardFooter, Container, Flex, Heading, IconButton, Image, Link, Stack, Text } from "@chakra-ui/react";
import Layout from "../components/Layout";
import articles from '../data/news-feed'

export default function News() {
  return (
    <Layout>
      <Container maxW='container.lg'>
        <Stack paddingY={10}>
          {articles.map((article, index) => (
            <Card
              key={`article-${index}`}
              direction={{ base: 'column', sm: 'row' }}
              overflow='hidden'
              variant='outline'
            >
              <Image
                objectFit='cover'
                maxW={{ base: '100%', sm: '400px' }}
                src={article.img}
              />
              <Stack>
                <CardBody>
                  <Heading size='md'>{article.title}</Heading>
                  <Text fontStyle={'italic'}>{article.date}</Text>
                  <Text>{article.description}</Text>
                </CardBody>
                <CardFooter paddingTop={0}>
                  <Button colorScheme={'purple'} variant={'outline'}>Czytaj dalej</Button>
                </CardFooter>
              </Stack>
            </Card>
          ))}
        </Stack>
        <Flex justifyContent={'center'} paddingTop={3}>
          <Link href={'/news'}>
            <IconButton
              variant={'outline'}
              icon={<ChevronLeftIcon />}
              marginX={'1'}
            />
          </Link>
          {articles.map((article, index) => {
            return (
              <Link href={'/news'} key={`page-${index}`}>
                <IconButton
                  variant={'outline'}
                  marginX={'1'}
                  icon={<Text>{index}</Text>}
                />
              </Link>
            )
          })}
          <Link href={'/news'}>
            <IconButton
              variant={'outline'}
              icon={<ChevronRightIcon />}
              marginX={'1'}
            />
          </Link>
        </Flex>
      </Container>
    </Layout>
  )
}
