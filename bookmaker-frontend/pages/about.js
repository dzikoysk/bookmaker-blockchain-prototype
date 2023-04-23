import { Container, Flex, Heading, Image, Stack, Text } from "@chakra-ui/react";
import Layout from "../components/Layout";

export default function Account() {
  return (
    <Layout>
      <Container maxW='container.lg'>
        <Image
          src='/images/about-banner.jpg'
          maxH='16'
          w='full'
          objectFit='cover'
          marginY={3}
          borderRadius='md'
        />

        <Heading size={'md'} paddingTop={4}>Czym jest nasza platforma?</Heading>
        <Flex alignItems={'center'} justifyContent={'space-between'}>
          <Text w='50%' textAlign={'justify'} paddingY={4}>
            Nasza platforma jest nowoczesnym i innowacyjnym rozwiązaniem,
            które umożliwia użytkownikom cieszenie się łatwym, bezpiecznym i transparentnym dostępem do zakładów bukmacherskich
            gwarantowanym przez wykorzystaną technologię blockchain.
            <br />
            <br />
            Zastosowanie najnowszych technologii pozwala nam na świadczenie usług na najwyższym poziomie,
            a jednocześnie zapewnia bezpieczeństwo transakcji i prywatności naszych użytkowników
            z dowolnego miejsca na świeci, o każdej porze dnia i nocy.
          </Text>
          <Flex w='50%' justifyContent={'center'}>
            <Image
              src="/images/landing.png"
              maxW='60%'
            />
          </Flex>
        </Flex>
        
        <Flex justifyContent={'space-between'}>
          <Flex w='49%' justifyContent={'center'}>
            <Image
              src="/images/monitoring.jpg"
              maxW='100%'
              objectFit={'cover'}
              borderRadius='xl'
            />
          </Flex>
          <Stack w='47%' justifyContent={'center'}>
            <Heading size={'md'} paddingTop={4}>Dlaczego warto?</Heading>
            <Text textAlign={'justify'}>
              Dzięki transparentnemu procesowi,
              nasi klienci mają pełną kontrolę nad swoimi środkami,
              a także pewność, że grają po uczciwych kursach.
              <br />
              <br />
              Gwarancja transparentności całego procesu wyklucza możliwość przybliżania kursu na korzyść bukmachera,
              dzięki czemu obstawiona pula w całości trafia do zwycięzców.
              Dzięki wykorzystanej technologii nie musisz nam wierzyć na słowo,
              możesz sprawdzić wszystkie transakcje na żywo, ponieważ nic nie ukrywamy!
            </Text>
          </Stack>
        </Flex>

        <Flex justifyContent={'space-between'} paddingY={8}>
          <Stack w='47%' justifyContent={'center'}>        
            <Heading size={'md'} paddingTop={4}>Co to jest blockchain?</Heading>
            <Text textAlign={'justify'}>
              Nie wiesz co to jest blockchain? Nie martw się, to nic trudnego!
              <br />
              Blockchain to technologia pozwalająca na tworzenie zdecentralizowanej i niezmiennej bazy danych.
              Każdy nowy blok z informacjami jest łączony z poprzednim blokiem, tworząc łańcuch bloków.
              Każda transakcja jest weryfikowana i potwierdzana przez wiele komputerów w sieci,
              co zapewnia bezpieczeństwo i niezmienność informacji.
              Blockchain pozwala na tworzenie bezpiecznych i transparentnych systemów,
              takich jak np. systemy płatności - w tym też naszą platformę zakładów bukmacherskich.
            </Text>
          </Stack>
          <Flex w='49%' justifyContent={'center'}>
            <Image
              src="/images/blockchain.png"
              maxW='100%'
              objectFit={'cover'}
              borderRadius='xl'
            />
          </Flex>
        </Flex>
      </Container>
    </Layout>
  )
}