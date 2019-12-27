import random
import math
import sys

def d(p, q):
    return (p[0] - q[0])*(p[0] - q[0]) + (p[1] - q[1])*(p[1] - q[1])

def Closest_Pair(P):
    Px = sorted(P, key=lambda tup: tup[0])
    Py = sorted(P, key=lambda tup: tup[1])
    P0_star, P1_star = Closest_Pair_Rec(Px, Py)
    return P0_star, P1_star

def Base_Case_CP(P):
    # Find smallest distance between two point using brute force: O(3)
    # at most since we only call when len(P) is at most 3, so O(1)
    bestDist = d(P[0], P[1])
    bestP = (P[0], P[1])
    for i in range(len(P)):
        for j in range(i+1, len(P)):
            newDist = d(P[i], P[j])
            if(newDist < bestDist):
                bestDist = newDist
                bestP = (P[i], P[j])
    return bestP[0], bestP[1]

def Closest_Pair_Rec(Px, Py):
    #fill in code for this function only
    
    # Base case! Once the number of points in the region is 2 or 3
    # we can find the min distance pair by simply checking all combo's
    lnX = len(Px)
    if(lnX < 4):
        return Base_Case_CP(Px)

    # Divide! Find halfway point for veritcal line, split x-sorted points
    # according to this index, and then use list comprehesion to
    # spilt y-sorted points into left and right halves (O(n))
    VertL = Px[(lnX-1)//2][0]
    Leftx = Px[:lnX//2]
    Rightx = Px[lnX//2:]
    Lefty = [p for p in Py if p[0] <= VertL]
    Righty = [p for p in Py if p[0] > VertL]

    # Conquer! Recursively find smallest pair in left and right halves
    P0_left, P1_left = Closest_Pair_Rec(Leftx, Lefty)
    P0_right, P1_right = Closest_Pair_Rec(Rightx, Righty)

    # Determine smallest distance between two and the best pair of points,
    # and create y-sorted list of points within delta of vertical line
    if(d(P0_left, P1_left) < d(P0_right, P1_right)):
        delta = d(P0_left, P1_left)
        bestPair = (P0_left, P1_left)
    else:
        delta = d(P0_right, P1_right)
        bestPair = (P0_right, P1_right)
    strip = [p for p in Py if abs(VertL - p[0]) <= delta]

    # Combine! Look for smaller pair within 15 positions of every point
    # in y-sorted list strip (O(15n) = O(n))
    for i in range(len(strip)):
        j = 1
        while (i + j < len(strip) and j < 16):
            newdist = d(strip[i], strip[i+j])
            if(newdist < delta):
                delta = newdist
                bestPair = (strip[i], strip[i+j])
            j += 1
    return bestPair[0], bestPair[1]
    
# do not change the code below
random.seed(12345)
P = []
for i in range(int(sys.argv[1])):
    P.append((random.random(), random.random()))

p1, p2 = Closest_Pair(P)
print(p1, p2, math.sqrt(d(p1, p2)))
