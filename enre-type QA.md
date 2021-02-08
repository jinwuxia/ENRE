When executing this statement, you may come across the following errors.

```
pip install -r requirements.txt
```

**Error 1:**

[![yl4xMT.png](https://s3.ax1x.com/2021/02/04/yl4xMT.png)](https://imgchr.com/i/yl4xMT)

**Solution 1:**

Put the *requirements.txt* on the  same folder as the ENRE-type2.0.jar, then re-run the above command.

**Error 2:**

There are four packages you need to install. *pytype* is one of them. When install *pytype*, it may pop put the error:

[![yl5Po9.png](https://s3.ax1x.com/2021/02/04/yl5Po9.png)](https://imgchr.com/i/yl5Po9)

detail error:

[![yl5KdH.png](https://s3.ax1x.com/2021/02/04/yl5KdH.png)](https://imgchr.com/i/yl5KdH)

**Solution 2:**

Downlowd the Microsoft visual c++ 14,  install it and reinstall pytype in Visual C++ 2015 x86 x64 Cross Build Tools Command Prompt. Like this:

[![yl5JQf.png](https://s3.ax1x.com/2021/02/04/yl5JQf.png)](https://imgchr.com/i/yl5JQf)

**Finally:**

Once all the problems have been solved, it will look like this:

[![yl5ylV.png](https://s3.ax1x.com/2021/02/04/yl5ylV.png)](https://imgchr.com/i/yl5ylV)