import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SkipList {

        public SkipListNode head;
        public SkipListNode tail;
        public int height;
        public Randomizer randomizer;
        private final int NEG_INF = Integer.MIN_VALUE;
        private final int POS_INF = Integer.MAX_VALUE;

        SkipList(){
            this.head = new SkipListNode(NEG_INF,1);
            this.tail = new SkipListNode(POS_INF,1);
            this.head.next.add(0,this.tail);
            this.tail.next.add(0,null);
            this.height = 1;
            this.randomizer = new Randomizer();
        }

        public boolean delete(int num){
            int he=height-1;
            SkipListNode temp=head;
            int n=num-1;
            for (int i=he;i>=0;i--){
                while (temp.next.get(i).value<=n){
                    temp=temp.next.get(i);
                }
            }
            SkipListNode temp2=temp.next.get(0);
            int size=temp2.height-1;
            temp=head;
            if (size==height-1) {
                for (int i = he; i >= 0; i--) {
                    while (temp.next.get(i).value < num) {
                        temp = temp.next.get(i);
                    }
                    if (temp.next.get(i).value == num) {
                        while (i >= 0) {
                            while (temp.next.get(i).value != num) {
                                temp = temp.next.get(i);
                            }
                            SkipListNode temp5 = temp.next.get(i).next.get(i);
                            temp.next.set(i, temp5);
                            i--;
                        }
                        int j = height - 1;
                        while (head.next.get(j) == tail && j>0) {
                            head.height--;
                            tail.height--;
                            height--;
                            head.next.remove(head.height);
                            j--;
                        }
                        return true;
                    }
                }
            }
            else {
                for (int i=size;i>=0;i--){
                    while (temp.next.get(i).value<num){
                        temp=temp.next.get(i);
                    }
                    if (temp.next.get(i).value==num){
                        while (i>=0) {
                            while (temp.next.get(i).value != num) {
                                temp = temp.next.get(i);
                            }
                            SkipListNode temp3 = temp.next.get(i).next.get(i);
                            temp.next.set(i, temp3);
                            i--;
                        }
                        return true;
                    }
                }
            }
            return false;
        }

        public boolean search(int num){
            SkipListNode temp=head;
            int he=height-1;
            if (temp==null){
                return false;
            }
            if (num==head.value || num==tail.value){
                return true;
            }
            for (int i=he;i>=0;i--){

                while (temp.next.get(i).value<=num){
                    temp=temp.next.get(i);
                }
                if (temp.value==num){
                    return true;
                }
            }
            return false;
        }

        public Integer upperBound(int num){ 
            /*
             * Takes an integer target as a parameter and returns the next larger integer in the skip list. Returns Integer.MAX_VALUE if no such integer exists.
             */
            int he=height-1;
            SkipListNode temp=head;
            for (int i=he;i>=0;i--){
                while (temp.next.get(i).value<=num){
                    temp=temp.next.get(i);
                }
            }
            return temp.next.get(0).value;
        }

        public void insert(int num){
            int numbers_height = 1;
            while (randomizer.binaryRandomGen()) {
                numbers_height++;
                if(numbers_height>height){
                    break;
                }
            }
            SkipListNode new_node = new SkipListNode(num, numbers_height);
            if (numbers_height > height) {
                height = numbers_height;
                head.height=head.height+1;
                tail.height=tail.height+1;
                head.next.add(tail);
                this.tail.next.add(0,null);
            }
            SkipListNode temp1 = head;

            for (int l=0;l<numbers_height;l++){
                new_node.next.add(null);
            }
            for (int i = (height - 1); i >= 0; i--) {
                //going ahead at the highest height possible
                while (temp1.next.get(i).value < num) {
                    temp1 = temp1.next.get(i);
                }
                //going down as new node's height is smaller.
                if (numbers_height < (i + 1)) {
                    continue;
                }
                //now we have reached the height of the new node
                else {
                    //going ahead at the height of the new node till we get the suitable position.
                    while (temp1.next.get(i).value < num) {
                        temp1 = temp1.next.get(i);
                    } 
                    //setting up of the new node at every possible height.
                    SkipListNode temp2 = temp1.next.get(i);
                    temp1.next.set(i, new_node);
                    new_node.next.set(i,temp2);
                }
            }
        }

        public void print(){
            //this function prints the skiplist 
            for(int i = this.height ; i>=1; --i){
                SkipListNode it = this.head;
                while(it!=null){
                    if(it.height >= i){
                        System.out.print(it.value + "\t");
                    }
                    else{
                        System.out.print("\t");
                    }
                    it = it.next.get(0);
                }
                System.out.println("null");
            }
        }
        public static void main(String[] args) {
            SkipList sk=new SkipList();
            Scanner sc=new Scanner(System.in);
            int ans=1;
            while(ans==1){
                System.out.println("select the operation do you want to perform");
                System.out.println("1) intialize a new skiplist");
                System.out.println("2) add element to already intialized skiplist");
                System.out.println("3) delete element from already intialized skiplist");
                System.out.println("4) print the skiplist");
                System.out.println("5) check if an element exists in the skiplist");
                System.out.println("6) to get the upper bound of an element from the skiplist");
                System.out.println("7) to end");
                int ef=sc.nextInt();
                sc.nextLine();
                if(ef==1){
                    System.out.println("enter the integers you want to put in the skiplist, write -1 and press return when you are done");
                    while(true){
                        int a=sc.nextInt();
                        if(a==-1){
                            break;
                        }
                        sk.insert(a);
                    }
                    sc.nextLine();
                    System.out.println("The updated skiplist is : ");
                    sk.print();
                }
                if(ef==2){
                    int numb=sc.nextInt();
                    sc.nextLine();
                    sk.insert(numb);
                    System.out.println("The updated skiplist is : ");
                    sk.print();
                }
                if(ef==3){
                    int numb=sc.nextInt();
                    sc.nextLine();
                    sk.delete(numb);
                    System.out.println("The updated skiplist is : ");
                    sk.print();
                }
                if(ef==4){
                    sk.print();
                }
                if(ef==5){
                    int numb=sc.nextInt();
                    sc.nextLine();
                    System.out.println(sk.search(numb));
                    // sk.print();
                }
                if(ef==6){
                    int numb=sc.nextInt();
                    sc.nextLine();
                    System.out.println(sk.upperBound(numb));
                }
                if(ef==7){
                    ans=0;
                }
            }
            // System.out.println("hello");
        }
}