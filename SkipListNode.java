import java.util.ArrayList;

public class SkipListNode{
    public int value;
    public int height;
    public ArrayList<SkipListNode> next;

    SkipListNode(int key,int height){
        value = key;
        this.height = height;
        next = new ArrayList<SkipListNode>(height);
        /*An ArrayList of size height, with pointers to next nodes of this node. Note that next[0] points to the next element in the base linked list (that is, list at hight 1),whereas next[h] points to the next node in the linked list at height h+1.*/
    }
}
