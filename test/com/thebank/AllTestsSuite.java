package com.thebank;

import com.thebank.controller.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({CustomersManagerTest.class, AccountsManagerTest.class})
public class AllTestsSuite {

}
