:- initialization(main).

% family.pl

% Facts
parent(tom, bob).
parent(tom, liz).
parent(bob, ann).
parent(bob, pat).
parent(liz, bill).
parent(ann, john).

% Rules
grandparent(X, Y) :- 
    parent(X, Z), 
    parent(Z, Y).

sibling(X, Y) :- 
    parent(P, X), 
    parent(P, Y), 
    X \= Y.

ancestor(X, Y) :- 
    parent(X, Y).

ancestor(X, Y) :- 
    parent(X, Z), 
    ancestor(Z, Y).

% Main predicate to run queries and print results
main :-
    (grandparent(tom, ann) -> write('Tom is a grandparent of Ann'), nl; write('Tom is not a grandparent of Ann'), nl),
    (sibling(bob, liz) -> write('Bob and Liz are siblings'), nl; write('Bob and Liz are not siblings'), nl),
    (ancestor(tom, john) -> write('Tom is an ancestor of John'), nl; write('Tom is not an ancestor of John'), nl),
    (sibling(bob, S) -> format('Bob has a sibling: ~w~n', [S]); write('Bob has no siblings'), nl),
    (grandparent(GP, pat) -> format('Pat has a grandparent: ~w~n', [GP]); write('Pat has no grandparents'), nl).


