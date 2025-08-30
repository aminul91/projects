
#include "mainwindow.h"
#include "ui_mainwindow.h"
#include "QMessageBox"

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::on_plusButton_clicked()
    {
        calculate('+');
    }

void MainWindow::on_minusButton_clicked()
    {
        calculate('-');
    }

void MainWindow::calculate(char op)

{
    if(op == '+')
     {
        double num1,num2,ans;
        num1 = ui -> number1input -> text().toDouble();
        num2 = ui -> number2input -> text().toDouble();
        ans = num1 + num2;
        QString str;
        str.setNum(ans);
        ui->displayAnswer->setText(str);
     }

    else if(op == '-')
     {
        double num1,num2,ans;
        num1 = ui -> number1input -> text().toDouble();
        num2 = ui -> number2input -> text().toDouble();
        ans = num1 - num2;
        QString str;
        str.setNum(ans);
        ui->displayAnswer->setText(str);
     }

    else if(op == '*')
     {
        double num1,num2,ans;
        num1 = ui -> number1input -> text().toDouble();
        num2 = ui -> number2input -> text().toDouble();
        ans = num1 * num2;
        QString str;
        str.setNum(ans);
        ui->displayAnswer->setText(str);
     }

    else if(op == '/')
     {
        double num1,num2,ans;
        num1 = ui -> number1input -> text().toDouble();
        num2 = ui -> number2input -> text().toDouble();
        ans = num1 / num2;
        QString str;
        str.setNum(ans);
        ui->displayAnswer->setText(str);
     }


}



void MainWindow::on_Multiply_clicked()
{
    calculate('*');
}

void MainWindow::on_divideButton_clicked()
{
    calculate('/');
}

void MainWindow::on_actionExit_triggered()
{
    exit(EXIT_SUCCESS);
}

void MainWindow::on_actionHelp_content_triggered()
{
    QMessageBox help;
    help.setWindowTitle("Guideline");
    help.setText("1 : insert two numbers.\n 2: clickbuttone according to operator\n 3: see the result ");
    help.exec();
}

void MainWindow::on_actionAbout_triggered()
{
    QMessageBox about;
    about.setWindowTitle("About");
    about.setText("1 : This calculator is developed by Md Aminul Islam \n 2: Version: 1.0");
    about.exec();
}
