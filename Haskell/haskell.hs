doubleMe x = x + x
doubleUs x y = doubleMe x + doubleMe y
doubleSmallNumber :: Int -> Int
doubleSmallNumber x = if x > 100 then x else x*2
factorial :: (Integral a) => a -> a
factorial 0 = 1
factorial n = n * factorial (n -1)
bmiTell :: (RealFloat a) => a -> String
bmiTell bmi
	| bmi <= 18.5 = "You are underweight."
	| bmi <= 25.0 = "You are normal."
	| bmi <= 30.0 = "You are fat."
	| otherwise = "You are a whale!"

bmiCalculate :: (RealFloat a) => a -> a -> String
bmiCalculate height weight
  | weight / height ^ 2 <= 18.5 = "Underweight."
  | weight / height ^ 2 <= 25.0 = "Normal."
  | weight / height ^ 2 <= 30.0 = "Fat."
  | otherwise                   = "Whale!"

initials :: String -> String -> String
initials firstname lastname = [f] ++ ". " ++ [l] ++ "."
  where (f:_) = firstname
        (l:_) = lastname

compareWithHundred :: (Num a, Ord a) => a -> Ordering
compareWithHundred = compare 100

applyTwice :: (a -> a) -> a -> a
applyTwice f x = f (f x)

sudokuValues = [(7,(1,1,1),False),(9,(1,2,1),False),(0,(1,3,1),True),(0,(1,4,2),True),(0,(1,5,2),True),(0,(1,6,2),True),(3,(1,7,3),False),(0,(1,8,3),True),(0,(1,9,3),True),
		(0,(2,1,1),True),(0,(2,2,1),True),(0,(2,3,1),True),(0,(2,4,2),True),(0,(2,5,2),True),(6,(2,6,2),False),(9,(2,7,3),False),(0,(2,8,3),True),(0,(2,9,3),True),
		(8,(3,1,1),False),(0,(3,2,1),True),(0,(3,3,1),True),(0,(3,4,2),True),(3,(3,5,2),False),(0,(3,6,2),True),(0,(3,7,3),True),(7,(3,8,3),False),(6,(3,9,3),False),
		(0,(4,1,4),True),(0,(4,2,4),True),(0,(4,3,4),True),(0,(4,4,5),True),(0,(4,5,5),True),(5,(4,6,5),False),(0,(4,7,6),True),(0,(4,8,6),True),(2,(4,9,6),False),
		(0,(5,1,4),True),(0,(5,2,4),True),(5,(5,3,4),False),(4,(5,4,5),False),(1,(5,5,5),False),(8,(5,6,5),False),(7,(5,7,6),False),(0,(5,8,6),True),(0,(5,9,6),True),
		(4,(6,1,4),False),(0,(6,2,4),True),(0,(6,3,4),True),(7,(6,4,5),False),(0,(6,5,5),True),(0,(6,6,5),True),(0,(6,7,6),True),(0,(6,8,6),True),(0,(6,9,6),True),
		(6,(7,1,7),False),(1,(7,2,7),False),(0,(7,3,7),True),(0,(7,4,8),True),(9,(7,5,8),False),(0,(7,6,8),True),(0,(7,7,9),True),(0,(7,8,9),True),(8,(7,9,9),False),
		(0,(8,1,7),True),(0,(8,2,7),True),(2,(8,3,7),False),(3,(8,4,8),False),(0,(8,5,8),True),(0,(8,6,8),True),(0,(8,7,9),True),(0,(8,8,9),True),(0,(8,9,9),True),
		(0,(9,1,7),True),(0,(9,2,7),True),(9,(9,3,7),False),(0,(9,4,8),True),(0,(9,5,8),True),(0,(9,6,8),True),(0,(9,7,9),True),(5,(9,8,9),False),(4,(9,9,9),False)]

sudoku2 = [(7,(1,1,1),False),(9,(1,2,1),False),(6,(1,3,1),True),(8,(1,4,2),True),(5,(1,5,2),True),(4,(1,6,2),True),(3,(1,7,3),False),(2,(1,8,3),True),(1,(1,9,3),True),
		(2,(2,1,1),True),(4,(2,2,1),True),(3,(2,3,1),True),(1,(2,4,2),True),(7,(2,5,2),True),(6,(2,6,2),False),(9,(2,7,3),False),(8,(2,8,3),True),(5,(2,9,3),True),
		(8,(3,1,1),False),(5,(3,2,1),True),(1,(3,3,1),True),(2,(3,4,2),True),(3,(3,5,2),False),(9,(3,6,2),True),(4,(3,7,3),True),(7,(3,8,3),False),(6,(3,9,3),False),
		(1,(4,1,4),True),(3,(4,2,4),True),(7,(4,3,4),True),(9,(4,4,5),True),(6,(4,5,5),True),(5,(4,6,5),False),(8,(4,7,6),True),(4,(4,8,6),True),(2,(4,9,6),False),
		(9,(5,1,4),True),(2,(5,2,4),True),(5,(5,3,4),False),(4,(5,4,5),False),(1,(5,5,5),False),(8,(5,6,5),False),(7,(5,7,6),False),(6,(5,8,6),True),(3,(5,9,6),True),
		(4,(6,1,4),False),(6,(6,2,4),True),(8,(6,3,4),True),(7,(6,4,5),False),(2,(6,5,5),True),(3,(6,6,5),True),(5,(6,7,6),True),(1,(6,8,6),True),(9,(6,9,6),True),
		(6,(7,1,7),False),(1,(7,2,7),False),(0,(7,3,7),True),(0,(7,4,8),True),(9,(7,5,8),False),(0,(7,6,8),True),(0,(7,7,9),True),(0,(7,8,9),True),(8,(7,9,9),False),
		(0,(8,1,7),True),(0,(8,2,7),True),(2,(8,3,7),False),(3,(8,4,8),False),(0,(8,5,8),True),(0,(8,6,8),True),(0,(8,7,9),True),(0,(8,8,9),True),(0,(8,9,9),True),
		(0,(9,1,7),True),(0,(9,2,7),True),(9,(9,3,7),False),(0,(9,4,8),True),(0,(9,5,8),True),(0,(9,6,8),True),(0,(9,7,9),True),(5,(9,8,9),False),(4,(9,9,9),False)]

incrementValue :: Num t => (t, Bool) -> (t, Bool)
incrementValue (x,y) = if y then (x+1, y) else (x,y) 

sudokuSquare = (1, (1,1), False)

returnFirst (w, x, y) = w
returnSecond (w, x, y) = x
returnThird (w, x, y) = y

returnX :: (Num a) => (a, (a, a, a), Bool) -> a
returnX (a, (b, c, e), d) = b

returnY :: (Num a) => (a, (a, a, a), Bool) -> a
returnY (a, (b, c, e), d) = c

returnValue :: (Num a, Eq a) => [(a, (a, a, a), Bool)] -> (a,a,a) -> a
returnValue sudoku position = 
	[returnFirst square | square <- sudoku, returnFirst(position) == returnFirst(returnSecond square), returnSecond(position) == returnSecond(returnSecond square)] !! 0

checkHorizontal :: (Num a, Eq a) => (a, (a, a, a), Bool) -> [(a, (a, a, a), Bool)] -> Bool
checkHorizontal x y = 
	if returnFirst x `elem` [returnFirst a | a <- y, returnFirst(returnSecond x) == returnFirst(returnSecond a), returnSecond(returnSecond x) /= returnSecond(returnSecond a)] then True else False

checkVertical :: (Num a, Eq a) => (a, (a, a, a), Bool) -> [(a, (a, a, a), Bool)] -> Bool
checkVertical x y = 
	if returnFirst x `elem` [returnFirst a | a <- y, returnSecond(returnSecond x) == returnSecond(returnSecond a), returnFirst(returnSecond x) /= returnFirst(returnSecond a)] then True else False

checkBox :: (Num a, Eq a) => (a, (a, a, a), Bool) -> [(a, (a, a, a), Bool)] -> Bool
checkBox x y = 
	if returnFirst x `elem` [returnFirst a | a <- y, returnThird(returnSecond x) == returnThird(returnSecond a), returnFirst(returnSecond x) /= returnFirst(returnSecond a) && returnSecond(returnSecond x) /= returnSecond(returnSecond a) ] then True else False

buildSudoku :: (Num a, Eq a) => [(a, (a, a, a), Bool)] -> (a,a,a) -> [(a, (a, a, a), Bool)]
buildSudoku [] _ = []
buildSudoku ((a,(b,c,e),d):xs) (x,y,z) = 
	if (b == x) && (c == y) then (a+1,(b,c,e),d):buildSudoku xs (x,y,z) else (a,(b,c,e),d):buildSudoku xs (x,y,z)

incrementSquare :: (Num a, Eq a) => [(a, (a, a, a), Bool)] -> (a,a,a) -> [(a, (a, a, a), Bool)]
incrementSquare [] _ = []
incrementSquare ((9,(b,c,e),d):xs) (x,y,z) = 
	if (b == x) && (c == y) then (0,(b,c,e),d):incrementSquare xs (x,y,z) else (9,(b,c,e),d):incrementSquare xs (x,y,z)
incrementSquare ((a,(b,c,e),d):xs) (x,y,z) = 
	if (b == x) && (c == y) then (a+1,(b,c,e),d):incrementSquare xs (x,y,z) else (a,(b,c,e),d):incrementSquare xs (x,y,z)

nextSquare :: (Num a, Eq a) => (a,a,a) -> (a,a,a)
nextSquare (9,9,9) = (10,10,10)
nextSquare (x,9,a) = if x == 3 || x == 6 then (x+1,1,a+1) else (x+1,1,a-2)
nextSquare (x,y,a) = if y == 3 || y == 6 then (x,y+1,a+1) else (x,y+1,a)

previousSquare :: (Num a, Eq a) => (a,a,a) -> (a,a,a)
previousSquare (1,1,1) = error "No previous square"
previousSquare (x,1,a) = if x == 4 || x == 7 then (x-1,9,a-1) else (x-1,9,a+2)
previousSquare (x,y,a) = if y == 4 || y == 7 then (x,y-1,a-1) else (x,y-1,a)
 
currentSquare :: (Num a, Eq a) => [(a, (a, a, a), Bool)] -> (a,a,a) -> (a, (a, a, a), Bool)
currentSquare sudoku (x,y,z) = [(a,(b,c,e),d) | (a,(b,c,e),d) <- sudoku, b == x, c == y] !! 0

squareIsNot9 :: (Num a, Eq a) => (a, (a, a, a), Bool) -> Bool
squareIsNot9 (a, (_, _, _), _) = if a == 9 then False else True 

squareIs0 :: (Num a, Eq a) => (a, (a, a, a), Bool) -> Bool
squareIs0 (a, (_, _, _), _) = if a == 0 then True else False 

sudokuIsInvalid :: (Num a, Eq a) => [(a, (a, a, a), Bool)] -> (a,a,a) -> Bool
sudokuIsInvalid sudoku position = if (checkHorizontal (currentSquare sudoku position) sudoku || checkVertical (currentSquare sudoku position) sudoku || checkBox (currentSquare sudoku position) sudoku) then True else False

squareIsModifiable :: (Num a, Eq a) => (a, (a, a, a), Bool) -> Bool
squareIsModifiable (_, (_,_,_), d) = d

solvingFinished :: (Num a, Eq a) => (a,a,a) -> Bool
solvingFinished (10,10,10) = True
solvingFinished (_,_,_) = False

solveSquareTest :: (Num a, Eq a) => [(a, (a, a, a), Bool)] -> (a,a,a) -> String 
solveSquareTest sudoku position
	| solvingFinished position = "Square finished"
	| squareIs0 (currentSquare sudoku position) = "Square is 0."
	| (sudokuIsInvalid sudoku position) && (squareIsNot9 (currentSquare sudoku position)) && squareIsModifiable (currentSquare sudoku position) = "Sudoku invalid and square is not 9"
	| sudokuIsInvalid sudoku position && squareIsModifiable (currentSquare sudoku position) = "Sudoku is invalid and square is 9"
	| otherwise = "Sudoku is fine, moving on."


solveSquareTemplate :: (Num a, Eq a) => [(a, (a, a, a), Bool)] -> (a,a,a) -> [(a, (a, a, a), Bool)]
solveSquareTemplate sudoku position
	| solvingFinished position = sudoku
	| squareIs0 (currentSquare sudoku position) = solveSquareTemplate (incrementSquare sudoku position) position
	| (sudokuIsInvalid sudoku position) && (squareIsNot9 (currentSquare sudoku position)) && squareIsModifiable (currentSquare sudoku position) = solveSquareTemplate (incrementSquare sudoku position) position
	| sudokuIsInvalid sudoku position && squareIsModifiable (currentSquare sudoku position) = solveSquareTemplate (incrementSquare sudoku position) (previousSquare position)  
	| otherwise = solveSquareTemplate sudoku (nextSquare position)

solveSquare :: (Num a, Eq a) => [(a, (a, a, a), Bool)] -> (a,a,a) -> a -> [(a, (a, a, a), Bool)]
solveSquare sudoku position 0
	| solvingFinished position = sudoku
	| squareIs0 (currentSquare sudoku position) = solveSquare (incrementSquare sudoku position) position 0
	| (sudokuIsInvalid sudoku position) && (squareIsNot9 (currentSquare sudoku position)) && squareIsModifiable (currentSquare sudoku position) = solveSquare (incrementSquare sudoku position) position 0
	| sudokuIsInvalid sudoku position && squareIsModifiable (currentSquare sudoku position) = solveSquare (incrementSquare sudoku position) (previousSquare position) 1  
	| otherwise = solveSquare sudoku (nextSquare position) 0
solveSquare sudoku position 1
	| squareIsModifiable (currentSquare sudoku position) == False = solveSquare sudoku (previousSquare position) 1
	| squareIsNot9 (currentSquare sudoku position) = solveSquare (incrementSquare sudoku position) position 0
	| otherwise = solveSquare (incrementSquare sudoku position) (previousSquare position) 1





