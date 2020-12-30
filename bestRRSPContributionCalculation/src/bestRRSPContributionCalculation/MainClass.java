package bestRRSPContributionCalculation;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainClass {

	public static void main(String[] args) {
		RRSP_Tax_relationship_Analysis();
		Service service = new Service();
//		for(int i=0; i<15; i++) {
//			double taxableIncome = 60000 + 5000 * i;
//			service.getFederalTax(taxableIncome );
//		}
//		service.getFederalTax(100000);
		// generate report
	}

	private static void RRSP_Tax_relationship_Analysis() {
		Service service = new Service();
		InputParam input = readThisYearIncomeInfo();
		for (int amountRRSP = 1000; amountRRSP < 21000; amountRRSP = amountRRSP + 1000) {
//		double amountRRSP = 20000;
			input.setAmountRRSP(amountRRSP);
			DecimalFormat df = new DecimalFormat("#.####");
			df.setRoundingMode(RoundingMode.CEILING);
			double netIncome = service.getNetIncome(input);
			double federalTax = schedule1_FederalTax(input);
			double ontarioTax = form428_OntarioTax(input);
			double incomeTaxDeducted = input.getCanadianTaxPaid();
			double finalTaxOwe = federalTax + ontarioTax - incomeTaxDeducted;
			System.out.println("Net income: " + df.format(netIncome) + ": RRSP: " + amountRRSP
					+ " : Total Tax need to pay: " + df.format(finalTaxOwe));
//			System.out.println("federal Tax: " + df.format(federalTax));
//			System.out.println("ontario Tax: " + df.format(ontarioTax));
//			System.out.println("Total Tax need to pay: " + (df.format(federalTax + ontarioTax)));
		}
	}

	private static double schedule1_FederalTax(InputParam input) {
		Service service = new Service();

		// step A - federal non refundable tax credit
		double federalCredit = service.calculateFederalNonRefundableTaxCredit();
//		System.out.println("federalCredit: " + federalCredit);
		// step B - federal tax on taxable income
		double federalTax = service.getFederalTax(service.getNetIncome(input));
//		System.out.println("federalTax: " + federalTax);
		// step C - net tax
		double federalUSTaxCredit = service.getFederalForeignTaxCredit_T2209(input);
//		System.out.println("federalUSTaxCredit: " + federalUSTaxCredit);
		double netTax = federalTax - federalCredit - federalUSTaxCredit;
//		System.out.println("netTax: " + netTax);

		return netTax;
	}

	private static double form428_OntarioTax(InputParam input) {
		Service service = new Service();
		
		// step A - Ontario non refundable tax credit
		double ontarioCredit = service.calculateOntarioNonRefundableTaxCredit();
		// step B - Ontario tax on taxable income
		double ontarioTax = service.getProvincialTax(service.getNetIncome(input));
		// step C - net tax
		double ontarioUSTaxCredit = service.getOntarioForeignTaxCredit_T2036(input);
		double netTax = ontarioTax - ontarioCredit - ontarioUSTaxCredit;

		return netTax;
	}

	private static InputParam readThisYearIncomeInfo() {
		InputParam inputs = new InputParam(35000, 7000, 100000, 22000, 1.29);
		return inputs;
	}

}
