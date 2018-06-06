package com.example.demo

import org.springframework.data.repository.CrudRepository

/* ----------Spring Java Persistence API (JPA) Module----------------
 * The central interface in the Spring Data repository abstraction
 * is Repository.  It manages domain classes as well as domain class
 * type arguments. The interface acts primarily as a marker interface
 * to capture the types to work with and to help discover interfaces
 * that extend Repository.
 *
 * CrudRepository provides sophisticated CRUD functionality for the
 * entity class being managed.
 *
 * Query methods are declared in the interface.  The repository may derive
 * a store-specific query either by:
 *    - deriving the query from the method name directly
 *    - using a manually-defined query
 *
 * Query Creation:
 *    - The query builder mechanism in the Spring Data repository infrastructure
 *       strips the prefixes "find...By", "read...By", "count...By", and "get...By"
 *       from the method and starts parsing the rest of it.
 *    - The first "By" acts as a delimiter to indicate the start of queries criteria.
 *    - You can define conditions on entity properties and concatenate them with "And"
 *      and "Or".  You may also use "Between", "LessThan", "GreaterThan", "Like" and
 *      "IgnoreCase(...)"
 *    - You can use "OrderBy" to the query method by providing some direction ("Asc"
 *      or "Desc")
 *    - To use multiple properties without being ambiguous, use "_" between
 *      entity properties
 *
 */

interface CardRepository : CrudRepository<Card, Long> {
    fun findAllByOrderByAddedAtDesc(): Iterable<Card>
}

interface UserRepository : CrudRepository<User, String>