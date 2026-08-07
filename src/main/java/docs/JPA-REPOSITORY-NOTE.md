any derived query method against a converted-type field must use an explicit @Query with the domain type as the parameter — plain String/LocalDate parameters will compile fine but fail at runtime

//NB email is a converted type and will need this kind of query
@Query("SELECT COUNT(s) > 0 FROM StaffJpaEntity s WHERE s.workEmail = :email")
boolean existsByWorkEmailValue(@Param("email") Email email);