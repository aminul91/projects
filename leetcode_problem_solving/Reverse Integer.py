class Solution:
    def reverse(self, x: int) -> int:
        input_d  = x
        if input_d<0:
            xx=input_d*(-1) #temporary convet only positive
        else:
            xx=input_d
        range_int=len(str(xx))
        cp=[]
        for i in range(range_int):
            if(xx/10)>=0:
                yy=xx%10
                xx=xx/10
                yy=int(yy)
                cp.append(yy)
        lenm=len(cp)
        lent=lenm-1
        mult=0
        sum=0
        rp=[]
        for i in range(lenm):
            sum = sum + cp[i]*(10**lent)
            lent=lent-1
        if input_d<0:
            sum=sum*(-1) #temporary convet only positive
        else:
            sum=sum
        if (sum <= -2**31 or sum >= (2**31-1)):
            return 0
        else:
            return sum



