package com.tarys.nps.ai.impl;

import com.tarys.nps.ai.AI;

public class SuperSmartAI implements AI {
    @Override
    public String analyze(double nps) {
        if (nps < 7.0) {
            return "Customers hate your product!";
        } else if (nps < 9.0) {
            return "Customers don't mind to use your product.";
        } else { // NPS >= 9.0
            return "Great Job - Customers love your product!";
        }
    }
}
