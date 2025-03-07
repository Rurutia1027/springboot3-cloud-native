# Optimistic Locking in JPA

## Overview of Optimistic Locking

Optimistic locking is a strategy that allows multiple transactions to access the same data concurrently without locking
it. Instead of immediately locking the data, optimistic locking checks for data consistency only when the transaction is
committed. If any other transaction has modified the data during the course of the current transaction, an exception (
e.g., OptimisticLockException) is thrown, indicating a conflict. This mechanism is useful in systems where data
conflicts re expected to be rare but can be handled appropriately when they occur.

## How Optimistic Locking Works:

In optimistic locking, the idea is to allow multiple transactions to read the same data but ensure that they cannot
commit changes to the same record unless no other transaction has modified t in the meantime.

The key concept in optimistic locking is a **version** field (or **timestamp**) that is included in the entity. Each
time the entity is updated, the version is incremented. When a transaction attempts to update the entity, it checks the
version number in the database to see if it matches the version it had when it first read the entity. If they don't
match, it indicates that another transaction has modifies the entity in the meantime, and a conflict occurs.

## Implementing Optimistic Locking in JPA
### Using `@Version` Annotation 

### 

##       
