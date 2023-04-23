import { Flex } from "@chakra-ui/react"
import Footer from "./Footer"
import Nav from "./Navbar"

export default function Layout({ children }) {
  return (
    <>
      <Flex minH={'100vh'} flexDirection='column'>
        <Nav />
        <Flex minH={'80vh'}>
          {children}
        </Flex>
        <Footer />
      </Flex>
    </>
  )
}