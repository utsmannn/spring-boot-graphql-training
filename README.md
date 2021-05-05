# Spring boot graphql

## Branch
Check branch for sample code

- Master (current)
- [2-crud](https://github.com/utsmannn/spring-boot-graphql-training/tree/2-crud)
- [3-jwt](https://github.com/utsmannn/spring-boot-graphql-training/tree/3-jwt)

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