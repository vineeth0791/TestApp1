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

  public Child()
  {

  }
  public  Child(int test,int test2)
  {
    super(test,test2);
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
      Parent parent = new Child(1,2);
      parent.method();

      Child child = new Child(1,2);
      child.method();

      Log.d("Inheritance","method varible"+parent.a);
      Log.d("Inheritance","method varible"+parent.variable);

      testInterface testInterface = new TestInterfaceClass();
      testInterface.testMethod();

  }

}
