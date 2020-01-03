# Solve 8 puzzle using game-tree and BFS

class Board:

    def __init__(self, entries):

        # Given a 1D array of 9 numbers, transfer to matrix

        if(len(entries) != 9):
            print("Error: enter 9 digits.")
            exit()    
        self._mEntries = [entries[:3], entries[3:6], entries[6:]]
        self._sEntries = set()
        self.Moves = []

        # Add matrix elements to set, save position of 0
       
        for x in range(3):
            for y in range(3):
                if self._mEntries[x][y] == 0:
                    self.blankPos = (x, y)
                self._sEntries.add(self._mEntries[x][y])

        # Use set to ensure we have correct number of elements
        
        for i in range(9):
            if i not in self._sEntries:
                print("Error. Please enter one occurance of each digit")
                exit()

    def copyB(self):

        # Create and return a copy of the current board
        lst = []
        for x in range(3):
            for y in range(3):
                lst.append(self._mEntries[x][y])
        newB = Board(lst)
        return newB

    def swap(self, i1, j1, i2, j2):

        # Swap 2 elemets in matrix
        self._mEntries[i1][j1], self._mEntries[i2][j2] = self._mEntries[i2][j2], self._mEntries[i1][j1]
        if self._mEntries[i1][j1] == 0:
            self.blankPos = (i1, j1)
        elif self._mEntries[i2][j2] == 0:
            self.blankPos = (i2, j2)
    
    def generateMoves(self):

        # Check boundry conditions: if we can swap 0 in a particular
        # direction, make a copy, swap in this direction and add this
        # copy to the array self.Moves
        i, j = self.blankPos
        if (i-1) >= 0:
            new = self.copyB()
            new.swap(i, j, i-1, j)
            self.Moves.append(new)
        if (j-1) >= 0:
            new = self.copyB()
            new.swap(i, j, i, j-1)
            self.Moves.append(new)
        if (i+1) <= 2:
            new = self.copyB()
            new.swap(i, j, i+1, j)
            self.Moves.append(new)
        if (j+1) <= 2:
            new = self.copyB()
            new.swap(i, j, i, j+1)
            self.Moves.append(new)


    def __eq__(self, otherB):

        # Two Boards with matrices of all the same numbers
        # in the same postions are equal
        for i in range(len(self._mEntries)):
            for j in range(len(self._mEntries[0])):
                if self._mEntries[i][j] != otherB._mEntries[i][j]:
                    return False
        return True

    def printBoard(self):
        arr = self._mEntries
        print('   ', str(arr[0][0]), '   ',
              str(arr[0][1]), '   ', str(arr[0][2]))
        print('   ', str(arr[1][0]), '   ',
              str(arr[1][1]), '   ', str(arr[1][2]))
        print('   ', str(arr[2][0]), '   ',
              str(arr[2][1]), '   ', str(arr[2][2]))

    def setRepresentation(self):

        # Returns an immutable representation of the board: we can
        # use this representation in a set
        arr = self._mEntries
        string = (str(arr[0][0]) + str(arr[0][1]) + str(arr[0][2]) +
                  str(arr[1][0]) + str(arr[1][1]) + str(arr[2][2]) +
                  str(arr[2][0]) + str(arr[2][1]) + str(arr[2][2]))
        return string


class Queue:

    # Queue implemented using an array: resizes only when needed.
    # Operations should stay constant
    
    def __init__(self):
        self.q = []
        self.head = 0

    def enqueue(self, x):
        self.q.append(x)

    def dequeue(self):
        head = self.q[self.head]
        self.head += 1
        if self.head > (len(self.q) // 2):
            self.q = self.q[self.head:]
            self.head = 0
        return head
            

# An implementation of BFS that tracks paths

def findSol(board):

    # Create solution board 
    solBoard = Board([1, 2, 3, 4, 5, 6, 7, 8, 0])

    # Create set of visited nodes, to prevent backtracking (we don't want
    # to make a move that takes us back to the board we were just at)
    visited = set()
    q = Queue()

    # Add to queue an array that is the path to first element 
    q.enqueue([board])
    while(len(q.q) > 0):

        # Dequeue a path and save last element in path, add this board
        # arragement to the visited set, then return path if last element
        # is the solution board
        currpath = q.dequeue()
        curr = currpath[-1]
        visited.add(curr.setRepresentation())
        if curr == solBoard:
            return currpath

        # Generate this boards possible moves: then iterate through each
        # of its moves, adding a new path array to queue only if the move
        # has not been visited (path array should end in current move)
        curr.generateMoves()
        for m in curr.Moves:
            if m.setRepresentation() not in visited:
                cpy = [move for move in currpath]
                cpy.append(m)
                q.enqueue(cpy)

    # If we do not return a path above, and exhaust all board permutations
    # reachable from a given start state, then there is no solution
    return False

def printPath(path):
    if(path):
        for i in range(len(path)):
            print('Step ', str(i), ' :')
            print('*'*18)
            path[i].printBoard()
            print('*'*18)
    else:
        print('No path found')


def main():
    print("Please enter 9 digits with entries 0-8.")
    entries = [int(i) for i in input().split()]
    board = Board(entries)
    path = findSol(board)
    if path:
        printPath(path)
    else:
        print('No Solution')

if __name__ == '__main__':
    main()

