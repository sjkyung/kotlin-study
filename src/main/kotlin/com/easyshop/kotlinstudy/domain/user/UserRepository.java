package com.easyshop.kotlinstudy.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.graphql.data.GraphQlRepository;
import org.springframework.stereotype.Repository;

@Repository
@GraphQlRepository
public interface UserRepository extends JpaRepository<User, Long> {
}
