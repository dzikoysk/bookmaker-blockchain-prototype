import { Container, Flex, Text } from "@chakra-ui/react"

export default function Footer() {
  return (
    <Flex>
      <Container>
        <Text textAlign={'center'} paddingY='4' fontSize={'sm'}>
          Copyright © 2023 Szymon Jazgara
        </Text>
      </Container>
    </Flex>
  )
}
