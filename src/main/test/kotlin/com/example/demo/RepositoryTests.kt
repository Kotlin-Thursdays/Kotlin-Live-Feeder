package com.example.demo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

/*@ExtendWith(SpringExtension::class)
@DataJpaTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RespositoryTests(@Autowired val entityManager: TestEntityManager,
                       @Autowired val cardRepository: CardRepository) {

    /* TestEntityManager methods:
     * - persist(E entity): Make an instance managed and persistent
     * - flush(): Synchronize the persistence context to the underlying database
     */

    @Test
    fun `When findById then return Card`() {
        val smaldini = User("smaldini", "Stéphane", "Maldini")
        entityManager.persist(smaldini)
        val card = Card("Reactor Bismuth is out", "Lorem ipsum", "dolor **sit** amet https://projectreactor.io/", smaldini)
        entityManager.persist(card)
        entityManager.flush()

        val found = cardRepository.findById(card.id!!)

        assertThat(found.get()).isEqualTo(card)
    }

}*/