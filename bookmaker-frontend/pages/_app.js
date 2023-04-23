import '@fontsource/raleway/500.css'
import '@fontsource/open-sans/500.css'

import { ChakraProvider } from '@chakra-ui/react'
import Head from 'next/head'

const criticalThemeCss = `
html, body {
  min-width: 460px;
  width: 100%;
}`

function App({ Component, pageProps }) {
  return (
    <>
      <Head>
        <title>Bookmaker Prototype</title>
        <style dangerouslySetInnerHTML={{ __html: criticalThemeCss }} />
      </Head>
      <ChakraProvider>
        <Component {...pageProps} />
      </ChakraProvider>
    </>
  )
}

export default App