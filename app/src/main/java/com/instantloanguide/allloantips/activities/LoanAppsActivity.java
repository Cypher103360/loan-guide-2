package com.instantloanguide.allloantips.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.instantloanguide.allloantips.R;
import com.instantloanguide.allloantips.adapter.LoanAppsAdapter;
import com.instantloanguide.allloantips.databinding.ActivityLoanAppsBinding;
import com.instantloanguide.allloantips.models.LoanAppModel;

import java.util.ArrayList;
import java.util.List;

public class LoanAppsActivity extends AppCompatActivity implements LoanAppsAdapter.LoanAppInterface {

    ActivityLoanAppsBinding binding;
    RecyclerView loanAppsRecyclerView;
    List<LoanAppModel> loanAppModelList = new ArrayList<>();
    LoanAppsAdapter loanAppsAdapter;
    String id, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoanAppsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        binding.actTitle.setText(title);

        binding.backBtn.setOnClickListener(view -> {
            onBackPressed();
        });

        loanAppsRecyclerView = binding.loanAppsRecyclerView;
        loanAppsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        loanAppsRecyclerView.setHasFixedSize(true);
        loanAppsAdapter = new LoanAppsAdapter(LoanAppsActivity.this, this);
        loanAppsRecyclerView.setAdapter(loanAppsAdapter);

        switch (id) {
            case "personalLoan":
                /*-->*/loanAppModelList.add(new LoanAppModel("1", R.drawable.avail_finance, "Avail Finance", "1.25% - 3% Per Month", "1000 - 40,000", "18+", "Aadhar Card, Pan Card, Bank Statement", "https://play.google.com/store/apps/details?id=com.avail.easyloans.android"));
                /*-->*/loanAppModelList.add(new LoanAppModel("2", R.drawable.bajaj_finserv, "Bajaj Finserv Ltd.", "12% to 34% Per Year", "Upto 25,00,000", "18+", "Aadhar Card, Pan Card Photo, Indian Citizen, 3 Month Bank Statement", "https://play.google.com/store/apps/details?id=org.altruist.BajajExperia"));
                /*-->*/loanAppModelList.add(new LoanAppModel("3", R.drawable.bharatpe_loan, "BharatPe Loan", "21% - 30% Per Year", "10,000 - 10 Lakh", "21+", "Aadhar Card, PAN Card Photo, 1 Signed ESC Auto Debit", "https://play.google.com/store/apps/details?id=com.bharatpe.app"));
                /*-->*/loanAppModelList.add(new LoanAppModel("4", R.drawable.cash_bean, "CashBean", "Upto 25.55% Per Year", "1,500 to 60,000", "18+", "Aadhar Card, Pan Card Photo, Indian Citizen", "https://play.google.com/store/apps/details?id=com.loan.cash.credit.easy.dhan.quick.udhaar.lend.game.jaldi.paisa.borrow.rupee.play.kredit"));
                /*-->*/loanAppModelList.add(new LoanAppModel("5", R.drawable.kredi_bee, "KreditBee", "0% - 29.95% Per Year", "1000 to 3,00,000", "21+", "Aadhar Card, Pan Card Photo, Indian Citizen", "https://play.google.com/store/apps/details?id=com.kreditbee.android"));
                /*-->*/loanAppModelList.add(new LoanAppModel("6", R.drawable.loan_front, "LoanFront", "12% - 35% Per Year", "5000 to 2,00,000", "21+", "Aadhar Card, Pan Card, Selfie, Income Proof", "https://play.google.com/store/apps/details?id=in.loanfront.android"));
                /*-->*/loanAppModelList.add(new LoanAppModel("7", R.drawable.money_view_loans, "Money View Loans", "16% - 39%* Per Year", "5,00,000", "21+", "Aadhar Card, Pan Card Photo, Indian Citizen,  Salary slip", "https://play.google.com/store/apps/details?id=com.whizdm.moneyview.loans"));
                /*-->*/loanAppModelList.add(new LoanAppModel("8", R.drawable.navi, "NAVI", "9.99% to 45% Per Year", "Up to ₹20,00,000", "18+", "Aadhar Card, Pan Card Photo, Indian Citizen", "https://play.google.com/store/apps/details?id=com.naviapp"));
                /*-->*/loanAppModelList.add(new LoanAppModel("9", R.drawable.nira, "NIRA", "2% - 3% Per Month", "Upto 1,00,000", "21+", "Aadhar Card, Pan Card, Selfie, Bank Statement", "https://play.google.com/store/apps/details?id=com.nirafinance.customer"));
                /*-->*/loanAppModelList.add(new LoanAppModel("10", R.drawable.phocket, "Phocket", "0% - 30% Per Year", "5,000 to 2,00,000", "20+", "Aadhar Card, Pan Card Photo, Bank Statement, Latest Salary Slip", "https://play.google.com/store/apps/details?id=com.phocket"));
                /*-->*/loanAppModelList.add(new LoanAppModel("11", R.drawable.rapid_rupee, "Rapid Rupee", "12% Per Year", "Upto 60,000", "22", "Aadhar Card, Pan Card, Selfie", "https://play.google.com/store/apps/details?id=co.afg.rupie"));
                /*-->*/loanAppModelList.add(new LoanAppModel("12", R.drawable.smart_coin, "Smart Coin", "0%-2.5% Per Month", "4,000 to 1,00,000", "21+", "Aadhar Card, Pan Card Photo", "https://play.google.com/store/apps/details?id=in.rebase.app"));
                /*-->*/loanAppModelList.add(new LoanAppModel("13", R.drawable.true_balance, "True Balance", "60% to 154.8% Per Year", "5,000 to 50,000", "21+", "Aadhar Card, Pan Card Photo, Indian Citizen, Selfie, Complete KYC", "https://play.google.com/store/apps/details?id=com.balancehero.truebalance"));


                break;
            case "bankLoan":
                /*-->*/loanAppModelList.add(new LoanAppModel("15", R.drawable.axis_bank, "Axis Bank", "10.49% - 17.25% Per Year", "50,000 to 25 lakh", "21 to 60", "Salaried Employees, Salaried Doctor, Employees of Private Limited Companies, Minimum Income 15000 Per Month", "https://play.google.com/store/apps/details?id=com.axis.mobile"));
                /*-->*/loanAppModelList.add(new LoanAppModel("16", R.drawable.bank_of_baroda, "Bank of Baroda", "10.50% - 12.50% Per Year", "1 Lakh to 15 lakh", "21+", "Aadhar Card, PAN Card Photo, Last 6 Month Bank Statement, 2-3 Passport Size Photo", "https://play.google.com/store/apps/details?id=com.bankofbaroda.mconnect"));
                /*-->*/loanAppModelList.add(new LoanAppModel("17", R.drawable.hdb, "HDB Financial", "11% Per Year", "1 Lakh to 20 lakh", "22 - 60", "Aadhar Card, PAN Card Photo, 2 Passport Size Photo, Last 3 Month Bank Statement.", "https://play.google.com/store/apps/details?id=com.nucleus.finnone.mobile.mserve.hdb.eng"));
                /*-->*/loanAppModelList.add(new LoanAppModel("18", R.drawable.hdfc_bank, "HDFC Bank", "10.50% to 21.00% Per Year", " 50,000 to 50 lakh", "21 - 60", "Aadhar Card, PAN Card Photo, Last 3 Month Bank Statement, 2 Latest Salary Slip", "https://play.google.com/store/apps/details?id=com.snapwork.hdfc"));
                /*-->*/loanAppModelList.add(new LoanAppModel("19", R.drawable.icic_bank, "ICICI Bank", "10% - 22% Per Year", "50,000 to 30 Lakh", "23 to 58", "Aadhar Card, PAN Card Photo, Last 3 Month Bank Statement, Last 3 Months Salary Slips, 2 Passport Size Photo", "https://play.google.com/store/apps/details?id=com.csam.icici.bank.imobile"));
                /*-->*/loanAppModelList.add(new LoanAppModel("20", R.drawable.idfc_first_bank, "IDFC First Bank", "Starting 10.49% Per Year", "1 Lakh to 40 lakh", "23 to 65", "Aadhar Card, PAN Card Photo, Last 3 Month Bank Statement, 2 Passport Size Photo", "https://play.google.com/store/apps/details?id=com.capitalfirst"));
                /*-->*/loanAppModelList.add(new LoanAppModel("21", R.drawable.iifl_my_money, "IIFL My Money", "14% Per Year", "10,000 to 10 lakh", "18+", "Aadhar Card, PAN Card Photo, Income Proof, Indian Citizen", "https://play.google.com/store/apps/details?id=com.iiflfinance.mymoney"));
                /*-->*/loanAppModelList.add(new LoanAppModel("22", R.drawable.kotak_mahindra_bank, "Kotak Mahindra Bank", "10.75% Per Year", "1 Lakh to 50 lakh", "25 - 55", "Aadhar Card, PAN Card Photo, Last 6 Month Bank Statement, Last 3 Months Income Details", "https://play.google.com/store/apps/details?id=com.msf.kbank.mobile"));
                /*-->*/loanAppModelList.add(new LoanAppModel("23", R.drawable.punjab_national_bank, "Punjab National Bank", "8% - 15% Per Year", "50,000 to 15 Lakh", "21 to 58", "Aadhar Card, PAN Card Photo, Last 3 Month Bank Statement, Last 3 Months Salary Slips, 2 Passport Size Photo", "https://play.google.com/store/apps/details?id=com.Version1"));
                /*-->*/loanAppModelList.add(new LoanAppModel("24", R.drawable.sbi_loan, "SBI Loans", "9% - 15% Per Year", "50,000 to 15 Lakh", "21 to 58", "Aadhar Card, PAN Card Photo, Last 6 Month Bank Statement, 2 Passport Size Photo", "https://play.google.com/store/apps/details?id=com.sbi.apps.sbi_loans"));


                break;
            case "businessLoan":
                /*-->*/loanAppModelList.add(new LoanAppModel("25", R.drawable.bajaj_finserv, "Bajaj Finserv Ltd.", "12% to 34% Per Year", "Upto 25,00,000", "18+", "Aadhar Card, Pan Card Photo, Indian Citizen, 3 Month Bank Statement", "https://play.google.com/store/apps/details?id=org.altruist.BajajExperia"));
                /*-->*/loanAppModelList.add(new LoanAppModel("26", R.drawable.chqbook_busines_loan, "CHQBOOK Business Loan", "1-2% Per Month", "50,000 - 10 Lakh", "21+", "Aadhar Card, PAN Card Photo Required", "https://play.google.com/store/apps/details?id=com.chqbook.customers"));
                /*-->*/loanAppModelList.add(new LoanAppModel("27", R.drawable.indifi, "INDIFI", "1.5% Per Month", "50 Lakh", "22", "Aadhar Card, PAN Card Photo Required, Business Turnover 5 Lakh, GST Ragistered Business, Business Operation 12 Months", "https://play.google.com/store/apps/details?id=com.riviera.indifi.app"));
                /*-->*/loanAppModelList.add(new LoanAppModel("28", R.drawable.lendingkart, "Lendingkart", "14% - 32% Per Year", "50 Lakh", "23+", "Aadhar Card, PAN Card Photo Required", "https://play.google.com/store/apps/details?id=com.LendingKart"));
                /*-->*/loanAppModelList.add(new LoanAppModel("28", R.drawable.money_tap, "Money Tap", "1.25% - 18% Per Year", "50 Lakh", "23+", "Original Passport Photo /  Selfie Self -Employees and Professional Skills Like Doctor, Lawyer, Shop Owner, Business Owner etc.", "https://play.google.com/store/apps/details?id=com.mycash.moneytap.app"));
                /*-->*/loanAppModelList.add(new LoanAppModel("28", R.drawable.money_view_loans, "Money View Loans", "16% - 39%* Per Year", "5,00,000", "21+", "Aadhar Card, Pan Card Photo, Indian Citizen,  Salary slip", "https://play.google.com/store/apps/details?id=com.whizdm.moneyview.loans"));
                /*-->*/loanAppModelList.add(new LoanAppModel("28", R.drawable.zip_loan, "Ziploan", "1.15% Per Month", "Upto 7.5 Lakh", "21+", "Aadhar Card, PAN Card Photo Required", "https://play.google.com/store/apps/details?id=com.ziploan.borrower"));


                break;
            case "homeLoan":
                loanAppModelList.add(new LoanAppModel("1", R.drawable.sbi_home_loans, "SBI Home Loans", "Starting at 6.65% Per Year", "50,000 to 50 Crores for Non Salaried", "Salaried or a Salf Employeed 18 to 76 Years",
                        "Aadhar Card, PAN Card, All Bank Account Last 6 Month Statements, Last 1 Year Loan Account Statement, \n" +
                        "For Salaried- Salary Slip of Last 3 Months, Copies of form 16 of last 2 Years, Copy of IT Retrun\n" +
                        "For Self Employeed - Business Address Proof, Last 3 Years Income Tax Returns, Last 3 years Balance Sheet, Profit & Loss A/c, Business Licence Details, TDS Certificate.  ", "https://play.google.com/store/apps/details?id=com.sbi.home_loans"));


                loanAppModelList.add(new LoanAppModel("1", R.drawable.navi, "Navi Home Loan App", "Starting at 6.71% yearly", "Upto 10 Crore", "21+", "Aadhar Card & Pan Card indian citizen", "https://play.google.com/store/apps/details?id=com.naviapp"));

                loanAppModelList.add(new LoanAppModel("1", R.drawable.idfc_first_bank, "IDFC First Bank", "Starting at 6.50", "Upto 10 Crore", "23 - 70",
                        "Aadhar Card, PAN Card, For Salaried: Last 2 months salary slip/ latest ITR or Form 16/ Last 6 months bank statement\n" +
                                "For Self-employed : Latest ITR/ balance sheet or P&L statement/ GST return/ last 6 months bank statements or CC statements, etc.\n" +
                                "\n" +
                                "Property Proof:- Photocopy of draft sale deed and chain title documents (if any)\n" +
                                "Allotment/Possession letter\n" +
                                "No objection certificate from the society and other documents as per legal report & Much More Etc.", "https://play.google.com/store/apps/details?id=com.capitalfirst"));


                break;
            case "aadharLoan":
                loanAppModelList.add(new LoanAppModel("1", R.drawable.creditt, "Creditt", "20% to 36% Each Year", "5,000 to 30,000", "21+", "Aadhar Card, PAN Card", "https://play.google.com/store/apps/details?id=com.creditt"));
                loanAppModelList.add(new LoanAppModel("1", R.drawable.kissht, "Kissht", "14% - 28% Per Year", "10,000/- to 1,00,000", "21+", "Aadhar Card, Pan Card Photo, Indian Citizen, Selfie, Complete KYC", "https://play.google.com/store/apps/details?id=com.fastbanking"));
                loanAppModelList.add(new LoanAppModel("1", R.drawable.kredi_bee, "KreditBee", "0% to 29.95% Each Year", "1000 to 30,000", "21+", "Aadhar Card, PAN Card", "https://play.google.com/store/apps/details?id=com.kreditbee.android"));
                loanAppModelList.add(new LoanAppModel("1", R.drawable.simply_cash, "SimplyCash", "Maximum 25% Per Year", "50,000 - 1,50,000", "21+", "Aadhar Card, Pan Card Photo, Indian Citizen, Selfie, Complete KYC", "https://play.google.com/store/apps/details?id=com.herofincorp.simplycash"));

                break;
            case "studentLoan":
                loanAppModelList.add(new LoanAppModel("1", R.drawable.badabro_student_loan_app, "BadaBro Student Loan App", "1% to 6% Per Month", "Up to 10,000", "18+", "Aadhar Card, PAN Card, College ID Card, Mark Sheet, Fulltime College Student", "https://play.google.com/store/apps/details?id=com.whileofone.badabro"));
                loanAppModelList.add(new LoanAppModel("1", R.drawable.credit_junior_student_loan_app, "Credit Junior Student Loan App", "2% to 6% Per Month", "2000 to 50,000", "18 to 25", "Aadhar Card, PAN Card, College ID Card, KYC Details", "https://play.google.com/store/apps/details?id=com.appybuilder.ajayhi0009.SIBrowser"));
                loanAppModelList.add(new LoanAppModel("1", R.drawable.mpokket_student_loan_app, "mPokket Student Loan App", "2% to 6% Per Month", "500 to 30,000", "18+", "Aadhar Card, PAN Card, College ID Card, KYC Details", "https://play.google.com/store/apps/details?id=com.mpokket.app"));
                loanAppModelList.add(new LoanAppModel("1", R.drawable.pocketly_student_loan_app, "Pocketly Student Loan App", "1% to 3% Per Month", "500 to 10,000", "18+", "Aadhar Card, PAN Card, College ID Card", "https://play.google.com/store/apps/details?id=com.mpokket.app"));
                loanAppModelList.add(new LoanAppModel("1", R.drawable.sahukar_student_loan_app, "Sahukar Student Loan App", "3% Per Month", "500 to 5,000", "18+", "Aadhar Card, PAN Card, College ID Card", "https://play.google.com/store/apps/details?id=com.sipl.udhaar"));
                loanAppModelList.add(new LoanAppModel("1", R.drawable.stucred_student_loan_app, "StuCred Student Loan App", "0% Per Year", "10,000", "18+", "Aadhar Card, PAN Card, College ID Card", "https://play.google.com/store/apps/details?id=com.kreon.stucred"));

                break;
        }

        loanAppsAdapter.updateLoanAppList(loanAppModelList);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onItemClicked(LoanAppModel loanAppModel, int position) {

    }
}