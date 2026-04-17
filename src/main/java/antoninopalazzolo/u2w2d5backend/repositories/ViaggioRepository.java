package antoninopalazzolo.u2w2d5backend.repositories;

import antoninopalazzolo.u2w2d5backend.entities.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ViaggioRepository extends JpaRepository<Viaggio, UUID> {
}
