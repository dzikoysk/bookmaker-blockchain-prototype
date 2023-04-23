import {v4 as uuidv4} from 'uuid'

const createBet = (date, title, closed, description) => ({
  betId: uuidv4(),
  operationId: "8cf2283ad6ef0a3266059b418a73f382",
  date,
  title,
  closed,
  bet: title.split(' vs ')[0],
  result: (Math.random() - 0.5) * 50,
  description: description ?? 'Brak opisu'
})

const usersBets = [
  createBet('2023-05-01', 'HT vs G2', false),
  createBet('2023-04-29', 'FNC vs G2', false),
  createBet('2023-04-28', 'HT vs MSF', false),
  createBet('2023-04-27', 'XL vs SK', false),
  createBet('2023-04-26', 'MAD vs G2', true),
  createBet('2023-04-25', 'KOI vs FNC', true),
  createBet('2023-04-24', 'FNC vs AST', true),
  createBet('2023-04-23', 'AST vs MAD', true),
  createBet('2023-04-22', 'XL vs G2', true),
  createBet('2023-04-21', 'FNC vs G2', true),
  createBet('2023-04-20', 'HT vs G2', true),
  createBet('2023-04-18', 'KOI vs SK', true),
]

usersBets[4].description = 'LEC Spring Split 2023 - MAD vs G2'

export default usersBets