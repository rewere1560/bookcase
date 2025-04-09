package com.example.bookcase.myCollection;

import com.example.bookcase.user.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectionRepository extends JpaRepository<MyCollection, Integer> {
    List<MyCollection> findByOwner(SiteUser owner);
    List<MyCollection> findByReleaseYN(Boolean releaseYN);
}
