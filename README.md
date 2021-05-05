# Spring graphql

### query
```graphql
type Query {
    login(username: String!, password: String!): Token!
    user: User
    users: [User]!
}

type Mutation {
    register(name: String!, username: String!, password: String!): User!
}

type User {
    id: Int!,
    name: String!,
    username: String!
}

type Token {
    token: String!
}
```

### Header
```json
{
  "Authorization" : "Bearer <your-generated-token>"
}
```