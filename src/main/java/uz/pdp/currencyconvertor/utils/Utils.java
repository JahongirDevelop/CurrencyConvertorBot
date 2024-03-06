package uz.pdp.currencyconvertor.utils;

import uz.pdp.currencyconvertor.repository.ConvertorRepository;
import uz.pdp.currencyconvertor.repository.UserRepository;

import java.util.ArrayList;
import java.util.Scanner;

public interface Utils {
    UserRepository userRepository = new UserRepository();
    ConvertorRepository convertorRepository = new ConvertorRepository();
    Scanner scanner = new Scanner(System.in);


}
