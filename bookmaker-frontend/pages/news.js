import {ChevronLeftIcon, ChevronRightIcon} from "@chakra-ui/icons";
import { Button, Card, CardBody, CardFooter, Container, Flex, Heading, IconButton, Image, Stack, Text } from "@chakra-ui/react";
import Layout from "../components/Layout";
import articles from '../data/news-feed'

export default function Account() {
  return (
    <Layout>
      <Container maxW='container.lg'>
        <Stack paddingY={10}>
          {articles.map((article, index) => (
            <Card
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
          <IconButton
            variant={'outline'}
            icon={<ChevronLeftIcon />}
            marginX={'1'}
          />
          {articles.map((article, index) => {
            return (
              <IconButton
                variant={'outline'}
                marginX={'1'}
                icon={<Text>{index}</Text>}
              />
            )
          })}
          <IconButton
            variant={'outline'}
            icon={<ChevronRightIcon />}
            marginX={'1'}
          />
        </Flex>
      </Container>
    </Layout>
  )
}
