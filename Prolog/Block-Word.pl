% Initialization

:- dynamic on/2.
:- dynamic move/3.

main :-
    test_on_ab,
    test_move_ab_table,
    test_clear_table,
    test_r_put_on,
    test_clear_off.

test_on_ab :-
    write('Testing on(a, b): '), nl,
    (on(a, b) -> write('on(a, b)'), nl ; write('on(a, b) failed'), nl).

test_move_ab_table :-
    write('Testing move(a, b, table): '), nl,
    (move(a, b, table) -> write('move(a, b, table)'), nl ; write('move(a, b, table) failed'), nl).

test_clear_table :-
    write('Testing clear(table): '), nl,
    (clear(table) -> write('clear(table)'), nl ; write('clear(table) failed'), nl).

test_r_put_on :-
    write('Testing r_put_on(A, B): '), nl,
    (r_put_on(A, B) -> write('r_put_on(A, B)'), write(': A = '), write(A), write(', B = '), write(B), nl ; write('r_put_on(A, B) failed'), nl).

test_clear_off :-
    write('Testing clear_off(A): '), nl,
    (clear_off(A) -> write('clear_off(A)'), write(': A = '), write(A), nl ; write('clear_off(A) failed'), nl).

on(a, b).
on(b, c).
on(c, table).

move(a, b, table).
move(b, c, table).
move(c, table, b).
move(c, b, table).
move(b, table, a).
move(c, table, b).

put_on(A, B) :-
    A \== table,
    A \== B,
    on(A, X),
    clear(A),
    clear(B),
    retract(on(A, X)),
    assert(on(A, B)),
    assert(move(A, X, B)).

clear(table).
clear(B) :-
    \+ on(_, B).

r_put_on(A, B) :-
    on(A, B).

r_put_on(A, B) :-
    \+ on(A, B),
    A \== table,
    A \== B,
    clear_off(A),
    clear_off(B),
    on(A, X),
    retract(on(A, X)),
    assert(on(A, B)),
    assert(move(A, X, B)).

clear_off(table).    % Means there is room on table
clear_off(A) :-      % Means already clear
    \+ on(_, A).
clear_off(A) :-
    A \== table,
    on(X, A),
    clear_off(X),
    retract(on(X, A)),
    assert(on(X, table)),
    assert(move(X, A, table)).

do(Glist) :-
    valid(Glist),
    do_all(Glist, Glist).

valid(_).

do_all([G|R], Allgoals) :- % already true now
    call(G),
    do_all(R, Allgoals), !. % continue with rest of goals

do_all([G|_], Allgoals) :- % must do work to achieve
    achieve(G),
    do_all(Allgoals, Allgoals). % go back and check previous goals

do_all([], _Allgoals). % finished

achieve(on(A, B)) :-
    r_put_on(A, B).

%-------------------------------------------Output Queries------------------------------->

% ?- on(a, b).
% true.

% ?- move(a, b, table).
% true.

% ?- clear(table).
% true ;
% false.

% ?- r_put_on(A, B).
% A = a,
% B = b ;
% A = b,
% B = c ;
% A = c,
% B = table.

% ?- clear_off(A).
% A = table ;
% false.

%----------------------Goal---------
% ?- achieve(on(A, B)).
% A = a,
% B = b ;
% A = b,
% B = c ;
% A = c,
% B = table.

:- main.