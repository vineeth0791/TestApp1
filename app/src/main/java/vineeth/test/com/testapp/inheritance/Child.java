package vineeth.test.com.testapp.inheritance;

import android.os.Bundle;
import android.util.Log;

public class Child extends Parent {
   int a = 100;
    public static int variable = 2;
   public void test()
  {
      System.out.print("a is "+a);
  }

  public void Child(int test,int test2)
  {

  }

    public void method()
    {
        System.out.println("Child class method");
    }

    public void method(int i)
    {

    }



  public void onCreate(Bundle saved)
  {
      super.onCreate(saved);
      context=Child.this;
      Parent parent = new Child();
      parent.method();

      Child child = new Child();
      child.method();
      Log.d("Inheritance","method varible"+parent.a);
      Log.d("Inheritance","method varible"+parent.variable);
  }

}
