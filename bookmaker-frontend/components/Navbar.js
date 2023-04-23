import { Box, Flex, Button, Stack, HStack, Image } from '@chakra-ui/react'
import { Link } from './Link'
import { FaUserAlt } from 'react-icons/fa'
import { useRouter } from 'next/router'

const link = (label, url) =>
  ({ label, url })

const Links = [
  link('Strona główna', '/'),
  link('O Platformie', '/about'),
  link('Aktualności', '/news'),
  link('Zakłady', '/bets'),
  link('Portfel', '/wallet'),
]

const NavLink = ({ link }) => {
  const router = useRouter()
  const selected = router.pathname === link.url

  return (
    <>
      <Link
        href={link.url}
        px={2}
        py={1}
        rounded={'md'}
        backgroundColor={'white'}
        fontWeight={'bold'}
        color={selected ? 'purple.500' : 'black'}
        textDecoration={selected ? 'underline' : 'none'}
        _hover={{ textDecoration: 'none', bg: 'gray.200' }}
      >
        {link.label}
      </Link>
    </>
  )
}

const AccountButton = ({ style }) => (
  <Link href='/account'>
    <Button style={style}>
      <FaUserAlt />
    </Button>
  </Link>
)
export default function Nav() {
  return (
    <>
      <Box style={{ backgroundColor: 'white' }} px={10}>
        <Flex
          alignItems={'center'}
          justifyContent={'space-between'}
          direction={{ base: 'column', md: 'row' }}
        >
          <Flex w='20%' justifyContent={{ base: 'center', md: 'start' }}>
            <Link href='/' py={4}>
              <Flex
                border={'1px solid black'}
                borderRadius={'xl'}
                paddingX={3}
                paddingBottom={1}
                fontSize={'md'}
                backgroundColor={'black'}
                color={'white'}
                fontWeight={'bold'}
                minW='140px'
              >
                <Image src='/images/logo.png' alt='Logo' boxSize={4} marginRight={2} marginTop={1.5}  />
                Bookmaker
              </Flex>
            </Link>
          </Flex>
          <HStack as={'nav'} spacing={3} w='80%' justifyContent={'center'} flexWrap={'wrap'}>
            {Links.map(link => (
              <NavLink key={link.label} link={link} paddingX={5} minW='200px' />
            ))}
          </HStack>
          <Flex alignItems={'center'} py={4} w='20%' justifyContent={{ base: 'center', md: 'end' }}>
            <Stack direction={'row'} spacing={4}>
              <AccountButton style={{ backgroundColor: 'gray.200' }} />
            </Stack>
          </Flex>
        </Flex>
      </Box>
    </>
  )
}
