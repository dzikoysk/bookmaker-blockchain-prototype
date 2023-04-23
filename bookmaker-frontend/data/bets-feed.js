const odds = (name, value) =>
  ({ name, value })

const bets = [
  {
    title: 'Fnatic VS EXCEL',
    event: '2023 LEC Spring Split',
    date: '11.03.2023 18:00',
    description: 'Mecz otwarcia fazy grupowej LEC 2023 Spring pomiędzy Fnatic i EXCEL Esports, które to w sezonie zimowym zajęły ostatnie miejsca w tabeli.',
    logo: '/images/articles/fncvsxl.jpg',
    odds: [
      odds('Fnatic', 1.7),
      odds('EXCEL', 1.3)
    ],
    open: true
  },
  {
    title: 'Team Heretics VS G2 Esports',
    event: '2023 LEC Spring Split',
    date: '11.03.2023 22:00',
    description: 'Ostatni mecz dnia fazy grupowej LEC 2023 Spring pomiędzy Team Heretics a G2 Esports.',
    logo: '/images/articles/thvsg2.jpg',
    odds: [
      odds('Team Heretics', 1.2),
      odds('G2 Esports', 3.8)
    ],
    open: true
  },
  {
    title: 'G2 Esports vs FPX',
    event: '2019 League of Legends Worlds Championship',
    date: '10.11.2019 12:00',
    description: 'Finałowy mecz mistrzostw świata League of Legends 2019 pomiędzy europejską drużyną G2 Esports a chińską FPX.',
    logo: '/images/articles/fpxvsg2.jpg',
    odds: [
      odds('G2 Esports', 1.9),
      odds('FPX', 2.1)
    ],
    open: false,
    result: '3-2'
  },
  {
    title: 'ROX Tigers VS KT Rolster',
    event: '2016 League of Legends Champions Korea: Summer Split',
    date: '13.07.2016 10:00',
    description: 'Finałowy mecz LCK 2017 Spring pomiędzy Koreańskimi drużynami ROX Tigers VS KT Rolster',
    logo: '/images/articles/roxvskt.jpg',
    odds: [
      odds('ROX Tigers', 1.5),
      odds('KT Rolster', 2.5)
    ],
    open: false,
    result: '3-2'
  }
]

export default bets