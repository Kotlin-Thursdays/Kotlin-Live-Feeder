package com.example.demo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
class RespositoryTests(@Autowired val entityManager: TestEntityManager,
                       @Autowired val userRepository: UserRepository,
                       @Autowired val cardRepository: CardRepository) {

    /* TestEntityManager methods:
     * - persist(E entity): Make an instance managed and persistent
     * - flush(): Synchronize the persistence context to the underlying database
     */

    @Test
    fun `When findById then return Card`() {
        val juergen = User("springjuergen", "Juergen", "Hoeller")
        entityManager.persist(juergen)
        val card = Card("Spring Framework 5.0 goes GA", "Dear Spring community ...", "Lorem ipsum", juergen)
        entityManager.persist(card)
        entityManager.flush()

        val found = cardRepository.findById(card.id!!)

        assertThat(found.get()).isEqualTo(card)
    }

}