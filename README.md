<h1 align="center">Welcome to Asteway's Financial Services</h1>
<h1 align="center">🏦 & Accounting Ledger System! 🏦</h1>

## Home Screen
<table>
  <tr>
    <td align="center" width="500">
      <img src="https://github.com/astewayn17/AccountingLedger/blob/main/screenshots/home_screen.png?raw=true" width="380"/><br/>
      <sub><i>Initial Home Screen</i></sub>
    </td>
    <td align="center" width="500">
      <img src="https://github.com/astewayn17/AccountingLedger/blob/main/screenshots/home_screen_exit.png?raw=true" width="380"/><br/>
      <sub><i>Exit Confirmation Screen</i></sub>
    </td>
  </tr>
</table>

Transactions:
<table>
  <tr>
    <td align="center" width="500">
      <img src="https://github.com/astewayn17/AccountingLedger/blob/main/screenshots/making_a_deposit.png" width="250"/><br/>
      <sub><i>Making a Deposit</i></sub>
    </td>
    <td align="center" width="500">
      <img src="https://github.com/astewayn17/AccountingLedger/blob/main/screenshots/making_a_payment.png" width="260"/><br/>
      <sub><i>Making a Payment</i></sub>
    </td>
  </tr>
</table>

## Ledger Screen
<div align="center">
  <table width="100%">
    <tr>
      <td align="center">
        <img src="https://github.com/astewayn17/AccountingLedger/blob/main/screenshots/ledger_screen.png?raw=true" width="420"/>
        <br/>
        <sub><i>Ledger Screen</i></sub>
      </td>
    </tr>
  </table>
</div>

Transaction Views:
<table>
  <tr>
    <td align="center" width="500">
      <img src="https://github.com/astewayn17/AccountingLedger/blob/main/screenshots/transaction_view_example_payments.png" width="340"/><br/>
      <sub><i>Showing Deposits Only</i></sub>
    </td>
    <td align="center" width="500">
      <img src="" width="330"/><br/>
      <sub><i>Showing Payments Only</i></sub>
    </td>
  </tr>
</table>

## Ledger Reports Screen
<div align="center">
  <table width="100%">
    <tr>
      <td align="center">
        <img src="https://github.com/astewayn17/AccountingLedger/blob/main/screenshots/ledger_reports_screen.png" width="420"/>
        <br/>
        <sub><i>Ledger Reports Screen</i></sub>
      </td>
    </tr>
  </table>
</div>

Report Transaction Views:
<table>
  <tr>
    <td align="center" width="500">
      <img src="https://github.com/astewayn17/AccountingLedger/blob/main/screenshots/transactions_for_beginning_of_month.png" width="360"/><br/>
      <sub><i>Transactions for Month to Date</i></sub>
    </td>
    <td align="center" width="500">
      <img src="https://github.com/astewayn17/AccountingLedger/blob/main/screenshots/transactions_for_vendor_search.png" width="330"/><br/>
      <sub><i>Transactions for Vendor Lookup</i></sub>
    </td>
  </tr>
</table>

## Interesting Code:
Condition used to acquire previous month transaction reports:
```java
case 2:
    transactions = Transactions.readTransactionsFromFile("src/main/resources/transactions.csv");
    found = false;
    for (Transactions t : transactions) {
    LocalDate actionDate = LocalDate.parse(t.getDate());
        if (actionDate.getMonth() == now.minusMonths(1).getMonth() && actionDate.getYear() == now.minusMonths(1).getYear()) {
            if (!found) {
                System.out.println("\nHere are your transactions from the previous month: \n");
            }
            System.out.println(t);
            found = true;
        }
    }
    if (!found) {
        System.out.println("\nReturned no transactions from the previous month.");
    }
    System.out.print("\nPress 'Enter' to return to the Ledger Reports Menu...");
    input.nextLine();
    break;
