/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: Scan
# Created by constantin at 16:40, Mär 12 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.command;

import me.constantindev.ccl.etc.PScanRunner;
import me.constantindev.ccl.etc.PortScannerManager;
import me.constantindev.ccl.etc.base.Command;
import me.constantindev.ccl.etc.helper.ClientHelper;

import java.net.InetAddress;

public class Scan extends Command {
    public Scan() {
        super("Scan", "Scans the given server for ports in range", new String[]{"scan"});
    }

    @Override
    public void onExecute(String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("cancel")) {
                PortScannerManager.killAllScans();
                ClientHelper.sendChat("Cancelled all scans");
                return;
            }
        }
        if (args.length < 5) {
            ClientHelper.sendChat("Usage: scan ip minPort maxPort threads delay");
            ClientHelper.sendChat("Example: scan localhost 0 65535 5 3");
            return;
        }
        if (!ClientHelper.isIntValid(args[1]) || !ClientHelper.isIntValid(args[2]) || !ClientHelper.isIntValid(args[3]) || !ClientHelper.isIntValid(args[4])) {
            ClientHelper.sendChat("The ports, threads, and delay must be a valid number");
            return;
        }
        InetAddress addr;
        try {
            addr = InetAddress.getByName(args[0]);
        } catch (Exception exc) {
            ClientHelper.sendChat("That appears to be an invalid ip address");
            return;
        }
        ClientHelper.sendChat("Started scanning " + args[0]);
        double minTime = (Double.parseDouble(args[2]) - Double.parseDouble(args[1])) * Double.parseDouble(args[4]);
        double maxTime = (Double.parseDouble(args[2]) - Double.parseDouble(args[1])) * 200 * Double.parseDouble(args[4]);
        ClientHelper.sendChat("Times:");
        ClientHelper.sendChat("  Best case:  " + (minTime / 1000 / Double.parseDouble(args[3])) + " seconds");
        ClientHelper.sendChat("  worst case: " + (maxTime / 1000 / Double.parseDouble(args[3])) + " seconds");
        PScanRunner pscR = new PScanRunner(addr, Integer.parseInt(args[3]), Integer.parseInt(args[4]), 200, Integer.parseInt(args[1]), Integer.parseInt(args[2]), scanResults -> {
            ClientHelper.sendChat("Done!");
            int ports = 0;
            for (PortScannerManager.ScanResult result : scanResults) {
                if (result.isOpen()) {
                    ClientHelper.sendChat("Port " + result.getPort() + " is open");
                    ports++;
                }
            }
            ClientHelper.sendChat("Total ports: " + scanResults.size());
            ClientHelper.sendChat("Open ports: " + ports);
            ClientHelper.sendChat("% ports: " + (((double) ports / scanResults.size()) * 100));
        });
        PortScannerManager.scans.add(pscR);
        super.onExecute(args);
    }
}
