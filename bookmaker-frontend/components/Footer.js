import { Container, Flex, Text } from "@chakra-ui/react"

export default function Footer() {
  return (
    <Flex>
      <Container maxW={'container.xl'}>
        <Text textAlign={'center'} paddingY='4' fontSize={'sm'}>
          Wizualny prototyp interfejsu | Praca inżynierska 117E-ISP-IZ/305446/1200369 | Copyright © 2023 Szymon Jazgara
        </Text>
      </Container>
    </Flex>
  )
}
