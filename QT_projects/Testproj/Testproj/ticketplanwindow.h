#ifndef ticketPLANWINDOW_H
#define ticketPLANWINDOW_H

#include <QWidget>

namespace Ui {
class ticketPlanWindow;
}

class ticketPlanWindow : public QWidget
{
    Q_OBJECT

public:
    explicit ticketPlanWindow(QWidget *parent = nullptr);
    ~ticketPlanWindow();
    void loadticketPlans();

private slots:
    void onlogOutClicked();

private:
    Ui::ticketPlanWindow *ui;
};

#endif // ticketPLANWINDOW_H
