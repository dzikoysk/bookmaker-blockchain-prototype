import { Box, Container, Flex, Heading, Spacer, VStack, Text } from "@chakra-ui/react"
import { Link } from './Link'

const link = (title, url) =>
  ({ title, url: url || "/" })

const guideLinks = [
  // link('Getting Started', '/guide/about'),
]

const communityLinks = [
  // link('Report Issue', 'https://github.com/dzikoysk/reposilite/issues/new/choose'),
]

export default function Footer() {
  return (
    <Flex>
      <Container>
        {/* <Flex justifyContent={'center'} paddingY={10}>
          <VStack>
            <Heading fontSize={16}>Guide</Heading>
            {guideLinks.map(({ title, url }) => (
              <Link key={title} href={url} fontSize={'sm'}>{title}</Link>
            ))}
          </VStack>
          <Spacer />
          <VStack>
            <Heading fontSize={16}>Community</Heading>
            {communityLinks.map(({ title, url }) => (
              <Link key={title} href={url} fontSize={'sm'}>{title}</Link>
            ))}
          </VStack>
        </Flex> */}
        <Text textAlign={'center'} paddingY='4' fontSize={'sm'}>
          Copyright © 2023 Szymon Jazgara
        </Text>
      </Container>
    </Flex>
  )
}
