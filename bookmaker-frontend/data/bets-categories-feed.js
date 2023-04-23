const categories = [
  {
    name: 'Wszystkie',
    subcategories: []
  },
  {
    name: 'Sport',
    subcategories: [
      {
        name: 'Piłka nożna',
      },
      {
        name: 'Piłka ręczna',
      },
      {
        name: 'Siatkówka',
      },
      {
        name: 'Koszykówka',
      },
      {
        name: 'Tenis',
      },
      {
        name: 'Boks',
      },
      {
        name: 'Hokej',
      },
      {
        name: 'Kolarstwo',
      }
    ]
  },
  {
    name: 'E-sport',
    subcategories: [
      {
        name: 'FIFA',
      },
      {
        name: 'League of Legends',
        selected: true,
      },
      {
        name: 'CS:GO',
      },
      {
        name: 'Fortnite',
      },
      {
        name: 'Valorant',
      },
      {
        name: 'Rocket League',
      },
    ]
  }
]

export default categories