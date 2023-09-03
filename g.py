# not accounting for an example like the following becasue of the line with *
# 2 2
# 4 3
# 1 2 0
# 2 1 0
import sys
tr=0
count = sys.stdin.readline().rstrip().split()
totalWealth = 0
def wealthfind(colony,tr,record,totalWealth):
    totalWealth+=int(wealth[colony-1])
    if tr == max:
        tr=0
        return totalWealth
    else:
        pass
    if record[colony] == []:
        tr=0
        return totalWealth
    else:
        pass
    for des in record[colony]:
        #print(des,destination)
        tr+=1
        return wealthfind(des,tr,record,totalWealth)

max = int(count[0])*int(count[1])

def tracking(colony,destination,tr,record):
    #print(record)
    # print(tr)
    if tr == max:
        tr=0
        return "True"
    else:
        pass
    if record[colony] == []:
        return "True"
    else:
        pass
    for des in record[colony]:
        #print(des,destination)
        if des == destination:
            tr=0
            return "False"
        else:
            tr+=1
            return tracking(des,destination,tr,record)
    


record={}
wealthrecord={}
final=[]
wealth = sys.stdin.readline().rstrip().split()
for x in range(1,int(count[0])+1):
    record[x] = []
for x in range(1,int(count[0])+1):
    wealthrecord[x] = [int(wealth[x-1])]
for x in range(int(count[1])):
    warpGate=sys.stdin.readline().rstrip().split()
    u=tracking(int(warpGate[0]),int(warpGate[1]),tr,record)
    if u == "True":
        if wealthrecord[int(warpGate[0])][0]>=int(warpGate[2]) and wealthrecord[int(warpGate[1])][0]>=int(warpGate[2]):
            final.append("BUILT")
            record[int(warpGate[0])].append(int(warpGate[1]))
            # * \record[int(warpGate[1])].append(int(warpGate[0]))
            wealthrecord[int(warpGate[0])][0]-=int(warpGate[2])
            wealthrecord[int(warpGate[1])][0]-=int(warpGate[2])
            r= wealthrecord[int(warpGate[0])][0]
            wealthrecord[int(warpGate[0])][0]+=wealthrecord[int(warpGate[1])][0]
            wealthrecord[int(warpGate[1])][0]+=r

            for x in range(len(wealthrecord[int(warpGate[1])])-1):
                wealthrecord[1+x][0]= wealthrecord[int(warpGate[1])][0]
            for x in range(len(wealthrecord[int(warpGate[0])])-1):
                wealthrecord[1+x][0]= wealthrecord[int(warpGate[0])][0]
            wealthrecord[int(warpGate[0])].append(int(warpGate[1]))
            wealthrecord[int(warpGate[1])].append(int(warpGate[0]))
        else:
            final.append("IMPOSSIBLE")
    else:
        final.append("UNNECESSARY")

print(final)