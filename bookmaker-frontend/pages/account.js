import { Alert, AlertIcon, Button, Container, Flex, Heading, Input, InputGroup, InputLeftElement, Stack } from "@chakra-ui/react";
import Layout from "../components/Layout";
import { AtSignIcon, LockIcon } from '@chakra-ui/icons'
import { Link } from "../components/Link";

export default function Account() {
  return (
    <Layout>
      <Container maxW='container.lg' minH={'90wh'} marginInlineStart={{ base: '0', sm: 'auto' }}>
        <Flex justifyContent={'center'} paddingTop={16}>
          <Stack
            shadow="xl"
            paddingX={16}
            paddingY={8}
            rounded={'xl'}
            alignItems={'center'}
            border={'1px solid gray'}
          >
            <Alert status='warning' rounded='xl'>
              <AlertIcon />
              Logowanie jest obecnie wyłączone
            </Alert>
            <Heading paddingY={6}>Login</Heading>
            <InputGroup marginY={1}>
              <InputLeftElement
                pointerEvents='none'
                children={<AtSignIcon color='gray.300' />}
              />
              <Input placeholder='Nazwa użytkownika' />
            </InputGroup>
            <InputGroup marginY={1}>
              <InputLeftElement
                pointerEvents='none'
                children={<LockIcon color='gray.300' />}
              />
              <Input type='password' placeholder='Hasło' />
            </InputGroup>
            <Link href={'/'} paddingTop={2} paddingBottom={3}>
              Zapomniałeś hasła?
            </Link>
            <Button colorScheme="purple" w='full'>Login</Button>
          </Stack>
        </Flex>
      </Container>
    </Layout>
  )
}