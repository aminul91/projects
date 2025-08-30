#ifndef WINDOWSECONDDIALOG_H
#define WINDOWSECONDDIALOG_H

#include <QDialog>

namespace Ui {
class windowSecondDialog;
}

class windowSecondDialog : public QDialog
{
    Q_OBJECT

public:
    explicit windowSecondDialog(QWidget *parent = 0);
    ~windowSecondDialog();

private:
    Ui::windowSecondDialog *ui;
};

#endif // WINDOWSECONDDIALOG_H
