# Spring graphql

### query
```graphql
type Query {
    hello(who: String): String!
    person: [Person!]!
}

type Mutation {
    addPerson(name: String!): Person!
}

type Person {
    id: Int!,
    name: String!
}
```