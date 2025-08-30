#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();

private slots:
    void on_plusButton_clicked();

    void on_minusButton_clicked();

    void on_Multiply_clicked();

    void on_actionExit_triggered();

    void on_divideButton_clicked();

    void on_actionHelp_content_triggered();

    void on_actionAbout_triggered();

private:
    Ui::MainWindow *ui;
    void calculate(char op);
};

#endif // MAINWINDOW_H
