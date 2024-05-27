:- initialization(main).
% Initialization
main :-
    canget(state(atdoor, onfloor, atwindow, hasnot), Plan1),
    write('Plan 1: '), write(Plan1), nl,
    (canget(state(atwindow, onbox, atwindow, hasnot), Plan2) -> 
        (write('Plan 2: '), write(Plan2), nl) 
        ; write('Plan 2: No solution'), nl),
    canget(state(Monkey, onfloor, atwindow, hasnot), Plan3),
    write('Monkey: '), write(Monkey), nl,
    write('Plan 3: '), write(Plan3), nl.

% Monkey-Banana Problem

% Initial state: Monkey is at door,
%                Monkey is on the floor,
%                Box is at window,
%                Monkey doesn't have banana.

% Prolog structure: structName(val1, val2, ...)

% state(Monkey location in the room, Monkey onbox/onfloor, box location, has/hasnot banana)

% Legal actions

do(state(middle, onbox, middle, hasnot),    % grab banana
   grab,
   state(middle, onbox, middle, has)).

do(state(L, onfloor, L, Banana),            % climb box
   climb,
   state(L, onbox, L, Banana)).

do(state(L1, onfloor, L1, Banana),          % push box from L1 to L2
   push(L1, L2),
   state(L2, onfloor, L2, Banana)).

do(state(L1, onfloor, Box, Banana),         % walk from L1 to L2
   walk(L1, L2),
   state(L2, onfloor, Box, Banana)).

% canget(State): monkey can get banana in State

canget(state(_, _, _, has)).                % Monkey already has it, goal state

canget(State1) :-                           % not goal state, do some work to get it
    do(State1, _, State2),                  % do something (grab, climb, push, walk)
    canget(State2).                         % canget from State2

% get plan = list of actions

canget(state(_, _, _, has), []).            % Monkey already has it, goal state

canget(State1, [Action|Plan]) :-            % not goal state, do some work to get it
    do(State1, Action, State2),             % do something (grab, climb, push, walk)
    canget(State2, Plan).                   % canget from State2

%-------------------Output Queries------------------------>
% ?- main.
% Plan 1: [walk(atdoor, middle), push(atwindow, middle), climb, grab].
% Plan 2: No solution
% Monkey: atwindow
% Plan 3: [push(atwindow, middle), climb, grab].
% Yes