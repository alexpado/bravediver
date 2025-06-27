package fr.alexpado.bravediver.repositories;

import fr.alexpado.bravediver.entities.Stratagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StratagemRepository extends JpaRepository<Stratagem, Integer> {

}
